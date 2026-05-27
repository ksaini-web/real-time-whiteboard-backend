package com.whiteboard.kartik.controller;

import com.whiteboard.kartik.dto.LoginRequest;
import com.whiteboard.kartik.dto.SignupRequest;
import com.whiteboard.kartik.dto.UserResponse;
import com.whiteboard.kartik.service.AuthSerice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthSerice authSerice;

    public AuthController(AuthSerice authSerice){
        this.authSerice = authSerice;
    }

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody SignupRequest request){

        return authSerice.signup(request);
    }


    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request){

        return authSerice.login(request);

    }






}
