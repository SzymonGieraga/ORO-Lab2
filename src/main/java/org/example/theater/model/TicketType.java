package org.example.theater.model;

public enum TicketType {
    NORMAL(1.0),
    REDUCED(0.9),
    STUDENT(0.7);

    private final double multiplier;

    TicketType(double multiplier){
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
