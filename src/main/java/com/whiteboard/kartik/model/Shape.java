package com.whiteboard.kartik.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Shape {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  private Long boardId;


   private  String type;

   @Lob
   @Column(columnDefinition = "LONGTEXT")
   private  String shapeData;
}
