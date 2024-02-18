package com.fruntier.fruntier.user.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Friend {
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

    public Friend() {
    }

    public FriendKey getFriendKey() {
        return friendKey;
    }

    public void setFriendKey(FriendKey friendKey) {
        this.friendKey = friendKey;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
