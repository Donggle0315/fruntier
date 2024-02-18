package com.fruntier.fruntier.user.domain;

public class FriendSearchDTO {
    Long id;
    String username;

    public FriendSearchDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "FriendSearchDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
