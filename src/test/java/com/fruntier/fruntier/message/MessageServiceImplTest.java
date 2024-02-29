package com.fruntier.fruntier.message;

import com.fruntier.fruntier.message.domain.Message;
import com.fruntier.fruntier.message.repository.MessageRepository;
import com.fruntier.fruntier.message.service.MessageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MessageServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImplTest.class);

    private final MessageRepository messageRepository;
    private final MessageService messageService;

    @Autowired
    MessageServiceImplTest(MessageRepository messageRepository, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @Test
    void fetchMessage(){

        List<Message> messageList=  messageService.fetchConversation("asdf","whatthe123");

        Assertions.assertThat(messageList).isNotEmpty();
        for (Message message : messageList) {
            logger.debug("message content : " + message.getContent());
            logger.debug("message sender : " + message.getSender());
            logger.info("message receiver : " + message.getReceiver());
            logger.info("message date : " + message.getTime());

        }
    }
}