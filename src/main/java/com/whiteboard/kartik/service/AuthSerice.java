package com.whiteboard.kartik.service;

import com.whiteboard.kartik.dto.LoginRequest;
import com.whiteboard.kartik.dto.SignupRequest;
import com.whiteboard.kartik.dto.UserResponse;
import com.whiteboard.kartik.model.User;
import com.whiteboard.kartik.repository.UserRepository;
import com.whiteboard.kartik.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthSerice {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthSerice(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserResponse signup(SignupRequest request) {



        User user = new User();

        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

     User savedUser   = userRepository.save(user);
     String token = jwtService.generateToken(savedUser.getEmail());

         UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setName(savedUser.getName());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setToken(token);

        return userResponse;
    }


    public UserResponse login(LoginRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isEmpty()){

            throw new RuntimeException("User not found");
        }

        User dbuser = existingUser.get();

        if(!dbuser.getPassword().equals(request.getPassword())){
            throw new RuntimeException("Wrong password");
        }

        String token = jwtService.generateToken(dbuser.getEmail());


        UserResponse userResponse = new UserResponse();
        userResponse.setId(dbuser.getId());
        userResponse.setName(dbuser.getName());
        userResponse.setEmail(dbuser.getEmail());
        userResponse.setToken(token);
        return userResponse;
    }




}
