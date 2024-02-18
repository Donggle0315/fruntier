package com.fruntier.fruntier.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //유저 구분자(PK)
    private String username; //the id of the user. identifiable
    private String name;//유저 이름
    private String password;//유저 패스 워드
    private String email;//유저 이메일
    private String address;
    private String phoneNumber; //유저 핸드폰 번호
    //private Image profile_picture;
    private String message;// 유저 프로필 메세지
    private Boolean isMale;//유저 성별
    private Tier tier;//유저 티어
    private Position position;//유저 등급(일반사용자, 관리자)
    private Date lastLoginDate; //마지막 접속 일자

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
