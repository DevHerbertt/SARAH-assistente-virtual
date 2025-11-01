package com.sarah.users;

import com.sarah.users.User;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;


@Entity
@SuperBuilder
public class CommonUser extends User {
}
