package com.example.chat.Controller;

import com.example.chat.Entity.Message;
import com.example.chat.Entity.MessageDto;
import com.example.chat.Entity.User;
import com.example.chat.Repository.MessageRepository;
import com.example.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;


    @Autowired
    public ChatController(MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    // 1. A user can start a chat with another user
    // 3. A user can send a message to another user
    // WebSocket Endpoint; Pass user in receiver field of chatMessage
    // Send message to receiver and sender in endpoint
    // Endpoint - /user/{receiverId}/queue/messages
    // Endpoint - /user/{senderId}/queue/messages
//    @MessageMapping("/sendMessage") // Receives messages from "app/sendMessage"

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload MessageDto messageDto) {
        Message message = messageService.createMessage(messageDto);
        messagingTemplate.convertAndSendToUser(message.getSender().getUsername(), "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(message.getReceiver().getUsername(), "/queue/messages", message);
    }
}

