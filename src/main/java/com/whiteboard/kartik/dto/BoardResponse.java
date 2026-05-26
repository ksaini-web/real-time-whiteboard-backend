package com.whiteboard.kartik.dto;

import com.whiteboard.kartik.model.Board;
import lombok.Data;

@Data
public class BoardResponse {

    private long id;
    private String title;
    private String boardCode;
    private boolean chatEnabled;
    private String role;
}
