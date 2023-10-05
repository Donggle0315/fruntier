package com.fruntier.fruntier.user;

public interface TierService {

    /**
     * 유저의 티어 정보를 얻음
     * @param UserId 유저 아이디
     * @return 유저의 현재 티어
     */
    Tier getUserTier(Long UserId);

    /**
     * 기록에 따라 유저의 티어를 측정하는 기능
     * @param userId 측정하고자 하는 유저 아이디
     * @return 해당 유저의 티어
     */
    Tier checkTier(Long userId);

    /**
     * 유저의 티어를 변경하는 기능
     * @param userId 변경할 유저 아이디
     * @param tier 변경될 티어
     */
    void changeUserTier(Long userId, Tier tier);
}
