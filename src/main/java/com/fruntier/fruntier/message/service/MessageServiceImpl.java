package com.fruntier.fruntier.message.service;


import com.fruntier.fruntier.message.domain.Message;
import com.fruntier.fruntier.message.repository.MessageRepository;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.globalexception.UserNotFoundException;
import com.fruntier.fruntier.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    public MessageServiceImpl(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;


    @Override
    public Message sendMessage(String senderUsername, String receiverUsername, String content) throws UserNotFoundException {
        Optional<User> opReceiver = userRepository.findByUsername(receiverUsername);
        Optional<User> opSender = userRepository.findByUsername(senderUsername);

        if (opReceiver.isEmpty()) {
            throw new UserNotFoundException("Could not find the Receiver of the Message");
        }
        if (opSender.isEmpty()) {
            throw new UserNotFoundException("Could not find the Sender of the Message");
        }

        User receiver = opReceiver.get();
        User sender = opSender.get();

        Message message = new Message();

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTime(new Date()); //fix of Adding date required

        messageRepository.save(message); //error checking of saving required.

        return message;
    }

    @Override
    public List<Message> fetchConversation(String loginUserUsername, String opponentUsername) {
        List<Message> messageListOne = messageRepository.findByReceiverUsernameAndSenderUsername(loginUserUsername, opponentUsername);
        List<Message> messageListTwo = messageRepository.findByReceiverUsernameAndSenderUsername(opponentUsername, loginUserUsername);

        List<Message> newList = new ArrayList<>();
        Stream.of(messageListOne, messageListTwo).forEach(newList::addAll);

        newList.sort(new MessageComparator());

        return newList;
    }

    private static class MessageComparator implements Comparator<Message> {
        @Override
        public int compare(Message o1, Message o2) {
            return o1.getTime().compareTo(o2.getTime());
        }

    }
}
