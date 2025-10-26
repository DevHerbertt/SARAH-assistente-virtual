package com.sarah.users;

import com.sarah.users.User;
import lombok.experimental.SuperBuilder;


public class AdminUser extends User {
    public AdminUser(String nome, String email) {
        super(nome, email);
    }
}
