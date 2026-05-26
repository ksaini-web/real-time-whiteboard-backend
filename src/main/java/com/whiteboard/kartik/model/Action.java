package com.whiteboard.kartik.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;
    private String actionType;
    private Long shapeId;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String oldData;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String newData;

    private String ShapeType;

    private Boolean undone = false;
}