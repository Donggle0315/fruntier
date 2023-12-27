package com.fruntier.fruntier.user.repository;

import com.fruntier.fruntier.user.domain.Friend;
import com.fruntier.fruntier.user.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryFriendRepository implements FriendRepository{

    private static List<Friend> friendStore = new ArrayList<>();


    @Override
    public Boolean saveFriend(Long userId1, Long userId2) {
        Friend fr1 = new Friend(userId1,userId2);
        Friend fr2 = new Friend(userId2, userId1);

        if(checkIsFriend(userId1,userId2)){
            return false;
        }else{
            friendStore.add(fr1);
            friendStore.add(fr2);

            return true;
        }

    }

    @Override
    public Boolean deleteFriend(Long userId1, Long userId2) {
        List<Friend> friendToDelete = friendStore.stream()
                .filter(f -> (Objects.equals(f.getUserId1(), userId1) && Objects.equals(f.getUserId2(), userId2))
                        || (Objects.equals(f.getUserId1(), userId2) && Objects.equals(f.getUserId2(), userId1)))
                .toList();

        if (!friendToDelete.isEmpty()) {
            friendStore.removeAll(friendToDelete);
            return true;
        }

        return false;

    }

    @Override
    public List<Long> getFriends(User user) {
        Long userId = user.getId();

        return friendStore.stream()
                .filter(f -> Objects.equals(f.getUserId1(), userId) || Objects.equals(f.getUserId2(), userId))
                .map(f -> Objects.equals(f.getUserId1(), userId) ? f.getUserId2() : f.getUserId1())
                .collect(Collectors.toList());
    }

    @Override
    public Boolean checkIsFriend(Long userId1, Long userId2) {
        Optional<Friend> findFriend = friendStore.stream()
                .filter(f -> (Objects.equals(f.getUserId1(), userId1) && Objects.equals(f.getUserId2(), userId2))
                        || (Objects.equals(f.getUserId1(), userId2) && Objects.equals(f.getUserId2(), userId1)))
                .findFirst();

        return findFriend.isPresent();
    }

    public void clearStore(){
        friendStore.clear();
    }
}
