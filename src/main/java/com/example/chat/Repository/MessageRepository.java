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

    List<Message> findBySenderIdAndReceiverId(int senderId, int receiverId);

    int countByReceiverIdAndSenderIdAndIsReadFalse(int receiverId, int senderId);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.isRead = true WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId AND m.isRead = false")
    int markMessagesAsRead(@Param("senderId") int senderId, @Param("receiverId") int receiverId);

    int countByReceiverIdAndIsReadFalse(int receiverId);
}
