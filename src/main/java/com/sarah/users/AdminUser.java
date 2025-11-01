package com.sarah.users;

import com.sarah.users.User;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
public class AdminUser extends User {

}
