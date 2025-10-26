package com.sarah.users;

import com.sarah.users.User;
import lombok.experimental.SuperBuilder;


public class CommonUser extends User {

    public CommonUser(String nome, String email) {
        super(nome, email);
    }
}
