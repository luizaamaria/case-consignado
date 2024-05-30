package com.example.clients.Entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jdk.jfr.Relational;

/**
 * ID,
 * CPF,
 * Nome,
 * InfoConvenio (pode ser outra entidade)
 */
@Entity
public class Client {
    private @Id Long id;
    private String name;

    public Client(){}

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
