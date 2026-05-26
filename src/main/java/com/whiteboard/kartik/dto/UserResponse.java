package com.whiteboard.kartik.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String token;
    String name;
    String email;
}
