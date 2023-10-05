package com.fruntier.fruntier.user;

import java.util.List;

public class User {
    private Long id;//유저 아이디(PK)
    private String name;//유저 이름
    private String password;//유저 패스 워드
    private String email;//유저 이메일
    private String phone_number; //유저 핸드폰 번호
    //private Image profile_picture;
    private String message;// 유저 프로필 메세지
    private boolean isMan;//유저 성별
    private Tier tier;//유저 티어
    private Position position;//유저 등급(일반사용자, 관리자)
    private List<User> friends;//유저 친구 목록
    private int last_login_date; //마지막 접속 일자
    public User(Long id, String name, String password, String email, String phone_number, String message, boolean isMan, Tier tier, Position position, List<User> friends, int last_login_date) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
        this.message = message;
        this.isMan = isMan;
        this.tier = tier;
        this.position = position;
        this.friends = friends;
        this.last_login_date = last_login_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public int getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(int last_login_date) {
        this.last_login_date = last_login_date;
    }
}
