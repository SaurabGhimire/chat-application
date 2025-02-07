package com.example.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.chat.Entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findBySenderAndReceiver(String sender, String receiver);

    int countByReceiverAndSenderAndIsReadFalse(String receiver, String sender);

    int countByReceiverAndIsReadFalse(String receiver);
}
