package org.example.theater.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationType {
    public static final String PENDING = "PENDING";
    public static final String ACCEPTED = "ACCEPTED";
    public static final String EXPIRED = "EXPIRED";

    @Id
    private Long id;

    private String name;

    public static ReservationType getPendingType() {
        return new ReservationType(1L, PENDING);
    }

    public static ReservationType getAcceptedType() {
        return new ReservationType(2L, ACCEPTED);
    }

    public static ReservationType getExpiredType() {
        return new ReservationType(3L, EXPIRED);
    }

    @Override
    public String toString() {
        return name;
    }
}