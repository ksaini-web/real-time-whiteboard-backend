package com.whiteboard.kartik.controller;

import com.whiteboard.kartik.service.ActionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actions")
@CrossOrigin("http://localhost:5173")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/undo/{boardId}")
    public String undo(@PathVariable Long boardId) {
        actionService.undo(boardId);
        return "Undo done";
    }

    @PostMapping("/redo/{boardId}")
    public String redo(@PathVariable Long boardId) {
        actionService.redo(boardId);
        return "Redo done";
    }
}