package org.example.theater;

import org.example.theater.model.*;
import org.example.theater.repo.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

@Slf4j
@SpringBootTest
class Oro2ApplicationTests {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private HallRepo hallRepo;

    @Autowired
    private PlayRepo playRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private ReservationTypeRepo reservationTypeRepo;

    @Autowired
    private TicketTypeRepo ticketTypeRepo;

    private ReservationType pendingType;
    private ReservationType acceptedType;
    private ReservationType expiredType;

    private TicketType normalType;
    private TicketType reducedType;
    private TicketType studentType;

    @BeforeEach
    void setup() {

        if (reservationTypeRepo.count() == 0) {
            reservationTypeRepo.save(ReservationType.getPendingType());
            reservationTypeRepo.save(ReservationType.getAcceptedType());
            reservationTypeRepo.save(ReservationType.getExpiredType());
        }

        if (ticketTypeRepo.count() == 0) {
            ticketTypeRepo.save(TicketType.getNormalType());
            ticketTypeRepo.save(TicketType.getReducedType());
            ticketTypeRepo.save(TicketType.getStudentType());
        }

        pendingType = reservationTypeRepo.findByName(ReservationType.PENDING);
        acceptedType = reservationTypeRepo.findByName(ReservationType.ACCEPTED);
        expiredType = reservationTypeRepo.findByName(ReservationType.EXPIRED);

        normalType = ticketTypeRepo.findByName(TicketType.NORMAL);
        reducedType = ticketTypeRepo.findByName(TicketType.REDUCED);
        studentType = ticketTypeRepo.findByName(TicketType.STUDENT);
    }

    @Test
    @Transactional
    void contextLoads() {
        Hall hall1 = createHall(1, 14);
        Hall hall2 = createHall(2, 15);
        Hall hall3 = createHall(3, 20);

        Client client1 = createClient("Jan", "Nowak", "jannowak@gmail.com", "jannowak");
        Client client2 = createClient("Andrzej", "Kowalski", "andrzejkowalski@wp.pl", "andrzejkowalski");

        Play play1 = createPlay("Sztuka numero 1");
        Play play2 = createPlay("Coś innego");
        Play play3 = createPlay("Nazwa 3 sztuki");

        LocalDateTime date1 = LocalDateTime.of(2020, 1, 25, 20, 15);
        LocalDateTime date2 = LocalDateTime.of(2020, 2, 26, 21, 30);
        LocalDateTime date3 = LocalDateTime.of(2020, 3, 27, 22, 45);

        Show show1 = createShow(hall1, play1, date1);
        Show show2 = createShow(hall2, play2, date1);
        Show show3 = createShow(hall3, play1, date2);
        Show show4 = createShow(hall3, play2, date3);
        Show show5 = createShow(hall3, play3, date1.plusDays(1));


        Ticket ticket1 = createTicket(LocalDateTime.now().plusMinutes(15), 105, show1, client1);
        Ticket ticket2 = createTicket(LocalDateTime.now().plusMinutes(10), 100, show1, client2);
        Ticket ticket3 = createTicket(LocalDateTime.now().plusMinutes(5), 99, show3, client1);
        Ticket ticket4 = createTicket(LocalDateTime.now().plusMinutes(12), 97, show2, client1);
        Ticket ticket5 = createTicket(LocalDateTime.now().plusMinutes(16), 24, show2, client2);

        log.info("-");
        log.info("-");
        log.info("-");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Show> hall1shows = showRepo.findByHallId(hall1.getId(), pageable);
        Page<Show> hall2shows = showRepo.findByHallId(hall2.getId(), pageable);
        Page<Show> hall3shows = showRepo.findByHallId(hall3.getId(), pageable);

        BiConsumer<Integer, Page<Show>> logHallShows = (number, shows) -> {
            log.info("Shows dla sali o numerze {}:", number);
            hall1shows.forEach(s -> log.info("Przedstawienie: {}, Data: {}", s.getPlay().getName(), s.getDate()));
        };

        logHallShows.accept(hall1.getNumber(), hall1shows);
        logHallShows.accept(hall2.getNumber(), hall2shows);
        logHallShows.accept(hall3.getNumber(), hall3shows);

        log.info("-");
        log.info("-");
        log.info("-");

        Page<Show> play1shows = showRepo.findByPlayId(play1.getId(), pageable);
        Page<Show> play2shows = showRepo.findByPlayId(play2.getId(), pageable);
        Page<Show> play3shows = showRepo.findByPlayId(play3.getId(), pageable);

        BiConsumer<Long, Page<Show>> logIdShows = (id, shows) -> {
            log.info("Przedstawienia dla sztuki o id = {}:", id);
            shows.forEach(show -> log.info("Sala: {}, Data: {}", show.getHall().getNumber(), show.getDate()));
        };

        logIdShows.accept(play1.getId(), play1shows);
        logIdShows.accept(play2.getId(), play2shows);
        logIdShows.accept(play3.getId(), play3shows);

        log.info("-");
        log.info("-");
        log.info("-");

        play1shows = showRepo.findByPlayName(play1.getName(), pageable);
        play2shows = showRepo.findByPlayName(play2.getName(), pageable);
        play3shows = showRepo.findByPlayName(play3.getName(), pageable);

        BiConsumer<String, Page<Show>> logNameShows = (name, shows) -> {
            log.info("Shows dla sztuki o nazwie {}:", name);
            shows.forEach(show -> log.info("Sala: {}, Data: {}", show.getHall().getNumber(), show.getDate()));
        };

        logNameShows.accept(play1.getName(), play1shows);
        logNameShows.accept(play2.getName(), play2shows);
        logNameShows.accept(play3.getName(), play3shows);

        log.info("-");
        log.info("-");
        log.info("-");

        Page<Client> showsClients = ticketRepo.findClientsByShowId(show1.getId(), pageable);

        log.info("Klienci dla show o id = {}", show1.getId());
        showsClients.forEach(client -> log.info("{} {}", client.getFirstName(), client.getLastName()));

        log.info("-");
        log.info("-");
        log.info("-");

        Page<Show> client1Shows = ticketRepo.findShowsByClientId(client1.getId(), pageable);
        Page<Show> client2Shows = ticketRepo.findShowsByClientId(client2.getId(), pageable);

        BiConsumer<Long, Page<Show>> logClientShows = (clientId, shows) -> {
            log.info("Shows klienta (id) = {}", clientId);
            shows.forEach(show ->
                    log.info("Nazwa: {} Data: {}", show.getPlay().getName(), show.getDate())
            );
        };

        logClientShows.accept(client1.getId(), client1Shows);
        logClientShows.accept(client2.getId(), client2Shows);

        log.info("-");
        log.info("-");
        log.info("-");

        client1Shows = ticketRepo.findShowsByClientLogin(client1.getLogin(), pageable);
        client2Shows = ticketRepo.findShowsByClientLogin(client2.getLogin(), pageable);

        BiConsumer<String, Page<Show>> logClientLoginShows = (clientLogin, shows) -> {
            log.info("Shows klienta (login) = {}", clientLogin);
            shows.forEach(show ->
                    log.info("Nazwa: {} Data: {}", show.getPlay().getName(), show.getDate())
            );
        };

        logClientLoginShows.accept(client1.getLogin(), client1Shows);
        logClientLoginShows.accept(client2.getLogin(), client2Shows);

        log.info("-");
        log.info("-");
        log.info("-");

        int hall1Place = ticketRepo.countOccupiedPlacesByHallNumberDate(hall1.getNumber(), date1);
        int hall2Place = ticketRepo.countOccupiedPlacesByHallNumberDate(hall2.getNumber(), date2);

        log.info("Zajęte miejsca w {}(numer sali) o {}: {}", hall1.getNumber(), date1, hall1Place);
        log.info("Zajęte miejsca w {}(numer sali) o {}: {}", hall2.getNumber(), date2, hall2Place);

        log.info("-");
        log.info("-");
        log.info("-");

        int Play1Halls = showRepo.countHallsByPlayId(play1.getId());
        int Play2Halls = showRepo.countHallsByPlayId(play2.getId());
        int Play3Halls = showRepo.countHallsByPlayId(play3.getId());

        log.info("Sale numer sztuki o id = {}: {}", play1.getId(), Play1Halls);
        log.info("Sale numer sztuki o id = {}: {}", play2.getId(), Play2Halls);
        log.info("Sale numer sztuki o id =  {}: {}", play3.getId(), Play3Halls);

        log.info("-");
        log.info("-");
        log.info("-");

        ticket1.setType(acceptedType);
        ticketRepo.save(ticket1);

        ticket2.setType(expiredType);
        ticketRepo.save(ticket2);

        ticket3.setType(acceptedType);
        ticketRepo.save(ticket3);

        ticket3.setType(acceptedType);
        ticketRepo.save(ticket4);

        ticket3.setType(acceptedType);
        ticketRepo.save(ticket5);

        int client1Tickets = ticketRepo.countTicketsByClientDateBetween(date1, date3, client1.getId());
        int client2Tickets = ticketRepo.countTicketsByClientDateBetween(date1, date3, client2.getId());

        log.info("Liczba biletów w przedziale  ({} - {}) klienta o id = {}: {}", date1, date3, client1.getId(), client1Tickets);
        log.info("Liczba biletów w przedziale ({} - {}) klienta o id = {}: {}", date1, date3, client2.getId(), client2Tickets);

        log.info("-");
        log.info("-");
        log.info("-");
        log.info("END");
        log.info("-");
        log.info("-");
        log.info("-");
    }

    private Hall createHall(int number, int places) {
        return hallRepo.save(new Hall(null, number, places));
    }

    private Play createPlay(String name) {
        return playRepo.save(new Play(null, name));
    }

    private Client createClient(String firstName, String lastName, String email, String login) {
        return clientRepo.save(new Client(null, firstName, lastName, email, login, null));
    }

    private Show createShow(Hall hall, Play play, LocalDateTime date) {
        return showRepo.save(new Show(null, hall, play, date));
    }

    private Ticket createTicket(LocalDateTime expireDate, long place, Show show, Client client) {
        return ticketRepo.save(new Ticket(null, expireDate, pendingType, normalType, place, client, show));
    }


}