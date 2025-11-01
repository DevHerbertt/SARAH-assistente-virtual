package com.sarah.utils;

import com.sarah.users.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class VerificationUsers {
    public int verification(User user){
        if (user instanceof AdminUser){
            log.debug("verification : AdminUser");
            return 1;
        }if (user instanceof CommonUser){
           log.debug("verification : CommonUser");
            return 2;
        }if (user instanceof KidsUser){
            log.debug("verification : KidsUser");
            return 3;
        }if (user instanceof UserManager){
            log.debug("verification : UserManager");
            return 4;
        }
        return 0;
    }
}
