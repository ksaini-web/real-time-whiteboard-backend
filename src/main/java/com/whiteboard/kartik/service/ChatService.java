package com.whiteboard.kartik.service;


import com.whiteboard.kartik.model.ChatMessage;
import com.whiteboard.kartik.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository){
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessage saveMessage(ChatMessage chatMessage){
        return chatMessageRepository.save(chatMessage);

    }

    public List<ChatMessage> getMessagesBYBoardId(Long boardId){
        return chatMessageRepository.findByBoardId(boardId);
    }
}
