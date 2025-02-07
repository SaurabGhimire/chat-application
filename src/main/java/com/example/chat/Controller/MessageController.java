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
        // Receiver should be a receiver_Id retrieved from SecurityContext
        // Delete all messages with senderId={senderId} and receiverId={receiverId}
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All messages sent from sender to receiver deleted");
    }

    // 4. A user can read a message from another user
    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public List<Message> getMessagesBySenderAndReceiver(@PathVariable String senderId,
                                                        @PathVariable String receiverId) {
        // TODO receiver should be a receiver_Id retrieved from SecurityContext
        return messageService.getMessagesBySenderAndReceiver(senderId, receiverId);
    }

    // 4. Mark that message/chat as read
    @PatchMapping("/{id}/read")
    public ResponseEntity<Message> updateMessageAsRead(@PathVariable int id) {
        Message message = messageService.markMessageAsRead(id);
        return ResponseEntity.ok(message);
    }

    // 5. A User can see the total number of unread messages they have both for a specific chat: count by a {sender}
    // 5. If {sender} null => Overall
    @GetMapping("/unread-count")
    public ResponseEntity<Integer> getUnreadMessageCount(@RequestParam String receiver, @RequestParam(required = false) String sender) {
        // TODO receiver should be a receiver_Id retrieved from SecurityContext
        int count = messageService.countUnreadMessages(receiver, sender);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/{sender}/readAll")
    public ResponseEntity<String> updateAllMessagesAsRead(@RequestParam String receiver,@PathVariable String sender) {
        // TODO receiver should be a receiver_Id retrieved from SecurityContext
        int updatedCount =  messageService.markMessagesAsRead(receiver, sender);
        return ResponseEntity.ok("Marked " + updatedCount + " messages as read.");
    }


}
