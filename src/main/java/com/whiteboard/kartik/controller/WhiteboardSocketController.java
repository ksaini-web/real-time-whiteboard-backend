package com.whiteboard.kartik.controller;

import com.whiteboard.kartik.model.ChatMessage;
import com.whiteboard.kartik.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WhiteboardSocketController {

    private final ChatService chatService;

    public WhiteboardSocketController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/draw")
    @SendTo("/topic/shapes")
    public Map<String, Object> sendShape(Map<String, Object> message) {
        System.out.println("Socket received: " + message);
        return message;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage sendChatMessage(ChatMessage message) {
        ChatMessage savedMessage = chatService.saveMessage(message);
        System.out.println("Chat message: " + savedMessage);
        return savedMessage;
    }
}