package com.whiteboard.kartik.controller;

import com.whiteboard.kartik.model.ChatMessage;
import com.whiteboard.kartik.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    public final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;

    }

//    @PostMapping
//    public ChatMessage save(@RequestBody ChatMessage chatMessage){
//
//        return chatService.saveMessage(chatMessage);
//    }

    @GetMapping("/{boardId}")
    public List<ChatMessage> findByBoardIchatMessaged(@PathVariable Long boardId){

        return chatService.getMessagesBYBoardId(boardId);
    }
}
