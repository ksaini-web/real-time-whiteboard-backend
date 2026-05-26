package com.whiteboard.kartik.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;
    private String username;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String message;

    private String time;
}
