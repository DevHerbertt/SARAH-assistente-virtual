package com.sarah.users;


import com.sarah.users.User;
import lombok.experimental.SuperBuilder;


public class UserManager extends User {
    public UserManager(String nome, String email) {
        super(nome, email);
    }
}
