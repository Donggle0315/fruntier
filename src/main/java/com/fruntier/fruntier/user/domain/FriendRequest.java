package com.fruntier.fruntier.user.domain;

import jakarta.persistence.*;

@Entity
public class FriendRequest {
    @EmbeddedId
    FriendRequestKey friendRequestId;

    @ManyToOne
    @MapsId("fromUserId")
    @JoinColumn(name = "from_user_id")
    User fromUser;

    @ManyToOne
    @MapsId("toUserId")
    @JoinColumn(name = "to_user_id")
    User toUser;

    public FriendRequest() {

    }

    public FriendRequestKey getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(FriendRequestKey friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
