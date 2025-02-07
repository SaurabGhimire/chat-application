package com.example.chat.Controller;

import com.example.chat.Entity.Message;
import com.example.chat.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // 2. A user can delete chat with another user
    @DeleteMapping("/{sender}/{receiver}")
    public ResponseEntity<String> deleteChat(@PathVariable String sender, @PathVariable String receiver ) {
        // Delete all messages with senderId={senderId} and receiverId={receiverId}
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All messages sent from sender to receiver deleted");
    }

    // 4. A user can read a message from another user
    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public List<Message> getMessagesBySenderAndReceiver(@PathVariable String senderId,
                                                        @PathVariable String receiverId) {
        return messageService.getMessagesBySenderAndReceiver(senderId, receiverId);
    }

    // 4. Mark that message/chat as read
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessageAsRead(@PathVariable int id) {
        Message message = messageService.markMessageAsRead(id);
        return ResponseEntity.ok(message);
    }

    // 5. A User can see the total number of unread messages they have both for a specific chat: count by a {sender}
    // 5. If {sender} null => Overall
    @GetMapping("/unread")
    public int getUnreadMessageCount(@RequestParam String receiver, @RequestParam(required = false) String sender) {
        return messageService.countUnreadMessages(receiver, sender);
    }


}
