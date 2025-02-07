package com.example.chat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.chat.Entity.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findBySenderAndReceiver(String sender, String receiver);

    int countByReceiverAndSenderAndIsReadFalse(String receiver, String sender);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.isRead = true WHERE m.sender = :sender AND m.receiver = :receiver AND m.isRead = false")
    int markMessagesAsRead(@Param("sender") String sender, @Param("receiver") String receiver);

    int countByReceiverAndIsReadFalse(String receiver);
}
