package com.sarah.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data

public abstract class User {

    protected String nome;

    protected String email;

    public User(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}