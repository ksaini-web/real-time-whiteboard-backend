package com.whiteboard.kartik.controller;

import com.whiteboard.kartik.dto.AcceptInviteRequest;
import com.whiteboard.kartik.dto.BoardResponse;
import com.whiteboard.kartik.dto.CreateBoardRequest;
import com.whiteboard.kartik.dto.InviteBoardRequest;
import com.whiteboard.kartik.model.Board;
import com.whiteboard.kartik.model.BoardInvite;
import com.whiteboard.kartik.model.BoardMember;
import com.whiteboard.kartik.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin("http://localhost:5173")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public Board createBoard(
            @RequestBody CreateBoardRequest board
    ) {
        return boardService.createBoard(board);
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public Board getBoardById(
            @PathVariable Long id
    ) {
        return boardService.getBoardById(id);
    }

//    @DeleteMapping("/{id}")
//    public String deleteBoard(
//            @PathVariable Long id
//    ) {
//        boardService.deleteBoard(id);
//        return "board deleted";
//    }


    @GetMapping("/user/{userId}")
    public List<BoardResponse> getBoardByUser(
            @PathVariable Long userId
    ) {
        return boardService.getBoardsByUser(userId);
    }

//    @PostMapping("/{boardId}/invite")
//    public BoardInvite inviteUser(
//            @PathVariable Long boardId,
//            @RequestBody InviteBoardRequest boardInvite
//    ) {
//        return boardService.inviteUser(boardId, boardInvite);
//    }

    @GetMapping("/invites/{email}")
    public List<BoardInvite> getPendingInvites(
            @PathVariable String email
    ) {
        return boardService.getPendingInvites(email);
    }

    @PostMapping("/invites/{inviteId}/accept")
    public BoardMember acceptInvite(
            @PathVariable Long inviteId,
            @RequestBody AcceptInviteRequest request
    ) {
        return boardService.acceptInvite(inviteId, request);
    }

    @GetMapping("/access/{boardCode}")
    public BoardResponse  getBoardAccess(
            @PathVariable String boardCode,
            @RequestParam Long userId
    ) {

        return boardService.getBoardByBoardCode(boardCode, userId);
    }

    @PostMapping("/{boardId}/invite")
    public BoardInvite inviteBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId,
            @RequestBody InviteBoardRequest boardInvite
    ) {

        return boardService.inviteUser(boardId, userId, boardInvite);

    }

    @PutMapping("/{boardId}/chat-toggle")
    public Board toggleChat(
            @PathVariable Long boardId,
            @RequestParam Long userId
    ) {
        return boardService.toggleChat(boardId, userId);
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId
    ) {
        boardService.deleteBoard(boardId, userId);
        return "board deleted";
    }


}
