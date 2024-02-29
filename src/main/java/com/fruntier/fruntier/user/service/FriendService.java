package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.FriendSearchDTO;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.FriendshipNotFoundException;

import java.util.List;

public interface FriendService {
    void makeFriendship(User f1, User f2);
    void breakFriendship(User f1, User f2) throws FriendshipNotFoundException;
    List<User> findFriendOfUser(User user);


    List<FriendSearchDTO> searchUser(User user, String key);

    void requestFriend(User fromUser, Long toUserId);

    void acceptRequestFriend(User curUser, Long requestingUserId);

    void cancelRequestFriend(User curUser, Long targetUserId);

    List<FriendSearchDTO> getFriendRequestSentList(User user);

    List<FriendSearchDTO> getFriendRequestIncomingList(User user);
}
