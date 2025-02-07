package com.example.chat.service;

import com.example.chat.Entity.Message;
import com.example.chat.Entity.MessageDto;
import com.example.chat.Entity.User;
import com.example.chat.Exception.MessageNotFoundException;
import com.example.chat.Repository.MessageRepository;
import com.example.chat.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message createMessage(MessageDto messageDto) {
        // TODO create UserNotFoundException
        User sender = userRepository.findById((long) Integer.parseInt(messageDto.getSenderId()))
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById((long) Integer.parseInt(messageDto.getReceiverId()))
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setIsRead(false);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesBySenderAndReceiver(int senderUsername, int receiverUsername) {
        return messageRepository.findBySenderIdAndReceiverId(senderUsername, receiverUsername);
    }

    // Mark a single message as read
    public Message markMessageAsRead(int id) {
        Message message = getMessageById(id);
        message.setIsRead(true);
        return messageRepository.save(message);
    }

    // Mark multiple messages as read
    public int markMessagesAsRead(int receiverId,int senderId){
        return messageRepository.markMessagesAsRead(senderId, receiverId);
    }

    public int countUnreadMessages(int receiverId, Integer senderId) {
        if (senderId != null) {
            return messageRepository.countByReceiverIdAndSenderIdAndIsReadFalse(receiverId, senderId);
        }
        return messageRepository.countByReceiverIdAndIsReadFalse(receiverId);
    }

    private Message getMessageById(int id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message with ID " + id + " not found"));
    }
}

