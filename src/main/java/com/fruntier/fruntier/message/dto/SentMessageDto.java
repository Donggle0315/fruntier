package com.fruntier.fruntier.message.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class SentMessageDto {
    String opponentUsername;
    String content;

}
