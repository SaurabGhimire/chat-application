package com.example.chat.service;

import com.example.chat.Entity.Message;
import com.example.chat.Exception.MessageNotFoundException;
import com.example.chat.Repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getMessagesBySenderAndReceiver(String senderUsername, String receiverUsername) {
        return messageRepository.findBySenderAndReceiver(senderUsername, receiverUsername);
    }

    // Create a new chat message
    public Message createMessage(Message message) {
        message.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Setting the creation time automatically
        return messageRepository.save(message);
    }

    // Update an existing chat message (e.g., mark as read)
    public Message markMessageAsRead(int id) {
        Message message = getMessageById(id);
        message.setIsRead(true);
        return messageRepository.save(message);
    }

    public int markMessagesAsRead(String receiver,String sender){
        return messageRepository.markMessagesAsRead(sender, receiver);
    }

    public int countUnreadMessages(String receiver, String sender) {
        if (sender != null) {
            return messageRepository.countByReceiverAndSenderAndIsReadFalse(receiver, sender);
        }
        return messageRepository.countByReceiverAndIsReadFalse(receiver);
    }

    private Message getMessageById(int id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message with ID " + id + " not found"));
    }


}

