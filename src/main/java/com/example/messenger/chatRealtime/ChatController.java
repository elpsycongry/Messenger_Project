package com.example.messenger.chatRealtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(Message message){
        return new Message();
    }

    @MessageMapping("/private-message")
    public Message recMessage(Message message){
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getConversation().getId()),"/private",message);
        return message;
    }
}