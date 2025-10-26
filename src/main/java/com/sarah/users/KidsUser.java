package main.java.com.sarah.users;

import com.sarah.users.User;
import lombok.experimental.SuperBuilder;


public class KidsUser extends User {

    public KidsUser(String nome, String email) {
        super(nome, email);
    }
}
