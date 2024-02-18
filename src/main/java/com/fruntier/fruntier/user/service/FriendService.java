package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.FriendSearchDTO;
import com.fruntier.fruntier.user.domain.User;

import java.util.List;

public interface FriendService {
    void makeFriendship(User f1, User f2);
    void breakFriendship(User f1, User f2);
    List<User> findFriendOfUser(User user);


    List<FriendSearchDTO> searchUser(User user, String key);

    void requestFriend(User fromUser, Long toUserId);

    List<FriendSearchDTO> getFromRequestFriendList(User user);

    List<FriendSearchDTO> getToRequestFriendList(User user);
}
