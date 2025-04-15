package org.example.theater.DTO;

import java.time.LocalDateTime;

public class ShowDTO {
    private Long id;
    private String playTitle;
    private int hallNumber;
    private LocalDateTime date;

    public ShowDTO(Long id, String playTitle, int hallNumber, LocalDateTime date) {
        this.id = id;
        this.playTitle = playTitle;
        this.hallNumber = hallNumber;
        this.date = date;
    }
}
