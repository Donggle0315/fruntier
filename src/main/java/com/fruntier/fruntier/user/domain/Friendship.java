package com.fruntier.fruntier.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Friendship {
    @EmbeddedId
    private FriendKey friendKey;

    @ManyToOne
    @MapsId("userId1")
    @JoinColumn(name="user_id_1")
    private User user1;

    @ManyToOne
    @MapsId("userId2")
    @JoinColumn(name="user_id_2")
    private User user2;

    public Friendship() {
    }
}
