package org.example.theater.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketType {
    public static final String NORMAL = "NORMAL";
    public static final String REDUCED = "REDUCED";
    public static final String STUDENT = "STUDENT";

    @Id
    private Long id;

    private String name;

    @Getter
    private double multiplier;

    public static TicketType getNormalType() {
        return new TicketType(1L, NORMAL, 1.0);
    }

    public static TicketType getReducedType() {
        return new TicketType(2L, REDUCED, 0.9);
    }

    public static TicketType getStudentType() {
        return new TicketType(3L, STUDENT, 0.7);
    }

    @Override
    public String toString() {
        return name;
    }
}