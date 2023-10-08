package com.fruntier.fruntier.user;

import java.util.Objects;

public class Friend {
    private Long userId1;
    private Long userId2;

    public Friend(Long userId1, Long userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public Long getUserId1() {
        return userId1;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj==null || getClass() != obj.getClass()) return false;

        Friend friend = (Friend)obj;
        return Objects.equals(userId1,friend.userId1) && Objects.equals(userId2,friend.userId2);
    }

    @Override
    public int hashCode(){
        return Objects.hash(userId1,userId2);
    }
}
