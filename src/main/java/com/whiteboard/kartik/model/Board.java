package com.whiteboard.kartik.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Board {


      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      private String title;


      private boolean chatEnabled = false;
      @Column(unique = true)
      private String boardCode;

}