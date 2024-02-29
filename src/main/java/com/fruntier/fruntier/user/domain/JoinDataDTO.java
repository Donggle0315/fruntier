package com.fruntier.fruntier.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDataDTO {
    String username;
    String password;
    String email;
    String name;
    String address;
    String sex;

    public boolean isNull(){
        if(username == null) return true;
        if(password == null) return true;
        if(email == null) return true;
        if(name == null) return true;
        if(address == null) return true;
        if(sex == null) return true;

        return false;
    }

    public boolean isMale(){
        return sex.equals("male");
    }

}
