package com.fruntier.fruntier.message.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadChatUserInfo {
    String loginUsername;
    String opponentUsername;
}
