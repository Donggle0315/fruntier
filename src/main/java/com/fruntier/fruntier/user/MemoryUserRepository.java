package com.fruntier.fruntier.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository{

    private static Map<Long,User> store = new HashMap<>();
    private static long sequence = 0L;
    @Override
    public Long createUser(User user) {
        int prevSize = store.size();
        user.setUserId(++sequence);
        store.put(user.getUserId(),user);

        return user.getUserId();
    }

    @Override
    public Optional<User> findUser(Long userId) {
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public Boolean updateUser(User user) {
        /*
            might be best to return User 객체.
            update 하고 나서 현재 로그인된(방금 업데이트된) user 객체를 전달해주면 어떨까
         */
        Optional<User> prevUser = Optional.ofNullable(store.get(user.getUserId()));

        if(prevUser.isPresent()){ //원래의 user 가 존재.
            store.put(user.getUserId(),user);
            return true;
        }else{//원래의 user 가 존재하지 않음
            return false;
        }

    }

    @Override
    public Boolean deleteUser(Long userId) {


        return store.remove(userId) != null;

    }

    public void clearMemberStore(){
        store.clear();
    }
}
