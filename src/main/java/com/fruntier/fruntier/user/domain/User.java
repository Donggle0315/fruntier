package com.fruntier.fruntier.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String name;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
    private String message;
    private Boolean isMale;
    private Tier tier;
    private Position position;
    private Date lastLoginDate;

    @OneToMany(mappedBy = "user1")
    private List<Friendship> friendshipList = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<FriendRequest> friendRequestIncomingList = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<FriendRequest> friendRequestSentList = new ArrayList<>();


    public User() {
        ;
    }

    public User(Long id, String username, String name,
                String password, String email, String phoneNumber,
                String message, Boolean isMale, Tier tier, Position position, Date lastLoginDate) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.isMale = isMale;
        this.tier = tier;
        this.position = position;
        this.lastLoginDate = lastLoginDate;
    }

    public static User fromUsername(String key) {
        User user = new User();
        user.setUsername(key);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}
