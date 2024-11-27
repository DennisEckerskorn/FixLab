package com.app.fixlab.models.persons;

import java.io.Serializable;

/**
 * PERSON CLASS: Child class of Person
 */
public class Client extends Person implements Serializable {
    public Client(String dni, String name, String surname, String email, String phoneNumber, String address, String username, String password) {
        super(dni, name, surname, email, phoneNumber, address, username, password);
    }
}
