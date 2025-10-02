package com.spring.realtimechatapp.DTO;


import com.spring.realtimechatapp.Enum.MessageType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChatMessage {
    private String sender;
    private String content;
    private MessageType type;

}
