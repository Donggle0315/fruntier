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
        friendship.setUser1(f1);
        friendship.setUser2(f2);

        f1.getFriendshipList().add(friendship);
        f2.getFriendshipList().add(friendship);
        friendshipRepository.save(friendship);

        userRepository.save(f1);
        userRepository.save(f2);
    }

    @Override
    public void breakFriendship(User f1, User f2) {
        FriendKey fk = new FriendKey();
        fk.setUserId1(f1.getId());
        fk.setUserId2(f2.getId());
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

        List<User> all = userRepository.findAll(userExample, pageRequest)
                .stream().filter(curUser -> !user.equals(curUser)).toList();
        return all.stream().map(curUser -> new FriendSearchDTO(curUser.getId(), curUser.getUsername())).toList();
    }

    @Override
    public void requestFriend(User fromUser, Long toUserId) {
        Optional<User> toUserOptional = userRepository.findById(toUserId);
        if(toUserOptional.isEmpty()){
            return; // don't do anything
        }

        User toUser = toUserOptional.get();
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFromUser(fromUser);
        friendRequest.setToUser(toUser);

        friendRequest = friendRequestRepository.save(friendRequest);

        fromUser.getFriendRequestToList().add(friendRequest);
        toUser.getFriendRequestFromList().add(friendRequest);

        userRepository.save(fromUser);
        userRepository.save(toUser);
    }

    @Override
    public List<FriendSearchDTO> getFromRequestFriendList(User user) {
        System.out.println("user.getFriendRequestFromList() = " + user.getFriendRequestFromList());
        return user.getFriendRequestFromList()
                .stream()
                .map(friendRequest -> {
                    User fromUser = friendRequest.getToUser();
                    return new FriendSearchDTO(fromUser.getId(), fromUser.getUsername());
                }).toList();
    }

    @Override
    public List<FriendSearchDTO> getToRequestFriendList(User user) {
        return user.getFriendRequestToList()
                .stream()
                .map(friendRequest -> {
                    User toUser = friendRequest.getFromUser();
                    return new FriendSearchDTO(toUser.getId(), toUser.getUsername());
                }).toList();
    }


}
