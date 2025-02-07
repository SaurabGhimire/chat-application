package com.example.chat.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    // TODO change sender username to sender id
    // TODO Map using @ManyToOne to user table
    private String sender;
    // TODO change sender username to sender id
    // TODO @ManyToOne to user table
    private String receiver;
    private boolean isRead;
    private Timestamp createdAt;

    public void setIsRead(boolean read) {
        this.isRead = true;
    }
}
