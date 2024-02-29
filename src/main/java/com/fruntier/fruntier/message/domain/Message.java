package com.fruntier.fruntier.message.domain;
import com.fruntier.fruntier.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User sender;
    @ManyToOne(fetch = FetchType.EAGER)
    private User receiver;

    @Column(nullable = false)
    private Date time;
    @Column(nullable = false)
    private String content;


    public Message(Long id, User sender, User receiver, Date time, String content) {
        Id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.content = content;
    }

    public Message() {
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }


}


