package com.whiteboard.kartik.dto;

import lombok.Data;

@Data
public class CreateBoardRequest {

    private String title;
    private Long userId;

}
