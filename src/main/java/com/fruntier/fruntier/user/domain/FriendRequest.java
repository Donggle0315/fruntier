package com.fruntier.fruntier.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}
