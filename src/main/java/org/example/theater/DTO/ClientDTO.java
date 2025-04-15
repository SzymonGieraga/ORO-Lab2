package org.example.theater.DTO;

public class ClientDTO {
    private String firstName;
    private String lastName;
    private String email;

    public ClientDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}