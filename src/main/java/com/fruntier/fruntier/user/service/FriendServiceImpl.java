package com.fruntier.fruntier.user.service;

import com.fruntier.fruntier.user.domain.*;
import com.fruntier.fruntier.user.repository.FriendRequestRepository;
import com.fruntier.fruntier.user.repository.FriendshipRepository;
import com.fruntier.fruntier.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Autowired
    public FriendServiceImpl(FriendshipRepository friendshipRepository, UserRepository userRepository, FriendRequestRepository friendRequestRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public void makeFriendship(User f1, User f2) {
        Friendship friendship = new Friendship();
        FriendKey friendKey = new FriendKey(f1.getId(), f2.getId());
        friendship.setFriendKey(friendKey);
        friendship.setUser1(f1);
        friendship.setUser2(f2);

        f1.getFriendshipList().add(friendship);

        friendshipRepository.save(friendship);

        friendship = new Friendship();
        friendKey = new FriendKey(f2.getId(), f1.getId());
        friendship.setFriendKey(friendKey);
        friendship.setUser1(f2);
        friendship.setUser2(f1);

        f2.getFriendshipList().add(friendship);
        friendshipRepository.save(friendship);

        userRepository.save(f1);
        userRepository.save(f2);
    }

    @Override
    public void breakFriendship(User f1, User f2) {
        FriendKey fk = new FriendKey(f1.getId(), f2.getId());
        Optional<Friendship> friendshipOptional = friendshipRepository.findById(fk);
        if (friendshipOptional.isEmpty()) {
            return; // do nothing
        }

        Friendship friendship = friendshipOptional.get();
        f1.getFriendshipList().remove(friendship);
        f2.getFriendshipList().remove(friendship);

        friendshipRepository.delete(friendship);

        userRepository.save(f1);
        userRepository.save(f2);
    }

    @Override
    public List<User> findFriendOfUser(User user) {
        return friendshipRepository.findAllByUser1(user);
    }

    @Override
    public List<FriendSearchDTO> searchUser(User user, String key) {
        Pageable pageRequest = PageRequest.of(0, 20);

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<User> userExample = Example.of(User.fromUsername(key), matcher);

        Stream<User> userStream = userRepository.findAll(userExample, pageRequest)
                .stream().filter(curUser -> {
                    boolean notCurrentUser = !user.equals(curUser);
                    boolean notFriendsAlready = !areFriends(curUser, user);
                    boolean notSentRequestAlready = !hasFriendRequest(user, curUser);
                    return notCurrentUser && notFriendsAlready && notSentRequestAlready;
                });
        return userStream.map(curUser -> new FriendSearchDTO(curUser.getId(), curUser.getUsername())).toList();
    }

    private boolean hasFriendRequest(User user, User curUser) {
        FriendRequestKey fk = new FriendRequestKey();
        fk.setFromUserId(user.getId());
        fk.setToUserId(curUser.getId());
        return friendRequestRepository.findById(fk).isPresent();
    }

    private boolean areFriends(User u1, User u2){
        FriendKey fk = new FriendKey();
        fk.setUserId1(u1.getId());
        fk.setUserId2(u2.getId());
        return friendshipRepository.findById(fk).isPresent();
    }

    @Override
    public void requestFriend(User fromUser, Long toUserId) {
        Optional<User> toUserOptional = userRepository.findById(toUserId);
        if (toUserOptional.isEmpty()) {
            return; // don't do anything
        }
        User toUser = toUserOptional.get();

        FriendRequest friendRequest = new FriendRequest();
        FriendRequestKey friendRequestKey = new FriendRequestKey(fromUser.getId(), toUserId);
        friendRequest.setFriendRequestId(friendRequestKey);
        friendRequest.setFromUser(fromUser);
        friendRequest.setToUser(toUser);

        fromUser.getFriendRequestSentList().add(friendRequest);
        toUser.getFriendRequestIncomingList().add(friendRequest);

        friendRequestRepository.save(friendRequest);
        userRepository.save(fromUser);
        userRepository.save(toUser);
    }

    @Override
    public void acceptRequestFriend(User curUser, Long requestingUserId) {
        Optional<User> userOptional = userRepository.findById(requestingUserId);
        if (userOptional.isEmpty()) {
            return; // do nothing
        }

        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(new FriendRequestKey(requestingUserId, curUser.getId()));
        if(friendRequestOptional.isEmpty()){
            return;
        }
        FriendRequest friendRequest = friendRequestOptional.get();

        User requestingUser = userOptional.get();
        makeFriendship(curUser, requestingUser);
        curUser.getFriendRequestIncomingList().remove(friendRequest);
        requestingUser.getFriendRequestSentList().remove(friendRequest);

        friendRequestRepository.delete(friendRequest);
        userRepository.save(curUser);
        userRepository.save(requestingUser);
    }

    @Override
    public void cancelRequestFriend(User curUser, Long targetUserId) {
        Optional<User> userOptional = userRepository.findById(targetUserId);
        if (userOptional.isEmpty()) {
            return; // do nothing
        }

        Optional<FriendRequest> friendRequestOptional = friendRequestRepository.findById(new FriendRequestKey(curUser.getId(), targetUserId));
        if(friendRequestOptional.isEmpty()){
            return;
        }
        FriendRequest friendRequest = friendRequestOptional.get();

        User targetUser = userOptional.get();
        curUser.getFriendRequestSentList().remove(friendRequest);
        targetUser.getFriendRequestIncomingList().remove(friendRequest);

        friendRequestRepository.delete(friendRequest);
        userRepository.save(curUser);
        userRepository.save(targetUser);
    }

    @Override
    public List<FriendSearchDTO> getFriendRequestSentList(User user) {
        return user.getFriendRequestSentList()
                .stream()
                .map(friendRequest -> {
                    User fromUser = friendRequest.getToUser();
                    return new FriendSearchDTO(fromUser.getId(), fromUser.getUsername());
                }).toList();
    }

    @Override
    public List<FriendSearchDTO> getFriendRequestIncomingList(User user) {
        return user.getFriendRequestIncomingList()
                .stream()
                .map(friendRequest -> {
                    User toUser = friendRequest.getFromUser();
                    return new FriendSearchDTO(toUser.getId(), toUser.getUsername());
                }).toList();
    }


}
