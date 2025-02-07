package com.example.chat.Controller;

import com.example.chat.Entity.Message;
import com.example.chat.Repository.MessageRepository;
import com.example.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
    // Endpoint - /user/{receiverUsername}/queue/messages
    // Endpoint - /user/{senderUsername}/queue/messages
    @MessageMapping("/sendMessage") // Receives messages from "app/sendMessage"
    public void sendMessage(@Payload Message message) {
        System.out.println(message.toString());
        message.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        messagingTemplate.convertAndSendToUser(message.getSender(), "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/messages", message);
        messageService.createMessage(message);
    }
}

