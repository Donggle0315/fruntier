package com.fruntier.fruntier.user;

import java.util.List;

public interface FriendRepository {

    void saveFriend(User user1, User user2);

    void deleteFriend(User user1, User user2);

    List<Friend> getFriends(User user1);

}
