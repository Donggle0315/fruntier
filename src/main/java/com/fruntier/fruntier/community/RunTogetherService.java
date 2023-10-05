package com.fruntier.fruntier.community;

public interface RunTogetherService {
    /**
     * 같이 달리기 위한 정보 등록 기능
     * @param peer_num 원하는 인원 수
     * @param date 원하는 날짜
     * @param time 원하는 시간
     * @param additional_content 추가 정보
     */
    void setRecrutingContent(int peer_num, int date, int time, String additional_content);

    /**
     * 약속 확정하는 기능 -> 참가자에게 알려줘야 함
     * @param done true 확정 / false 파토
     */
    void confirmAppointment(boolean done);

    /**
     * 그룹 참석
     * @param userId 참석할 유저 아이디
     * @param articleId 참석할 그룹 게시글 아이디
     */
    void joinGroup(Long userId, Long articleId);

    /**
     * 그룹 탈퇴
     * @param userId 탈퇴할 유저 아이디
     * @param articleId 탈퇴할 그룹 게시글 아이디
     */
    void leaveGroup(Long userId, Long articleId);


}
