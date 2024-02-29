package com.fruntier.fruntier.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FriendSearchDTO {
    Long id;
    String username;

    public FriendSearchDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
