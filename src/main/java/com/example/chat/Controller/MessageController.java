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
    @DeleteMapping("/sender/{senderId}")
    public ResponseEntity<String> deleteChat(@PathVariable int sender) {
        // Retrieve receiver from session
        // Delete all messages with senderId={senderId} and receiverId={receiverId}
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All messages sent from sender to receiver deleted");
    }

    // 4. A user can read a message from another user
    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public List<Message> getMessagesBySenderAndReceiver(@PathVariable int senderId,
                                                        @PathVariable int receiverId) {
        // TODO Retrieve receiverId from security context
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
    public ResponseEntity<Integer> getUnreadMessageCount(@RequestParam int receiver, @RequestParam(required = false) Integer sender) {
        // TODO Retrieve receiverId from security context
        int count = messageService.countUnreadMessages(receiver, sender);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/{sender}/readAll")
    public ResponseEntity<String> updateAllMessagesAsRead(@RequestParam int receiver,@PathVariable int sender) {
        // TODO Retrieve receiverId from security context
        int updatedCount =  messageService.markMessagesAsRead(receiver, sender);
        return ResponseEntity.ok("Marked " + updatedCount + " messages as read.");
    }


}
