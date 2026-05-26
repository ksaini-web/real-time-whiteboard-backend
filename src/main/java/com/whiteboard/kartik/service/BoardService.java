package com.whiteboard.kartik.service;

import com.whiteboard.kartik.dto.AcceptInviteRequest;
import com.whiteboard.kartik.dto.BoardResponse;
import com.whiteboard.kartik.dto.CreateBoardRequest;
import com.whiteboard.kartik.dto.InviteBoardRequest;
import com.whiteboard.kartik.model.Board;
import com.whiteboard.kartik.model.BoardInvite;
import com.whiteboard.kartik.model.BoardMember;
import com.whiteboard.kartik.repository.BoardInviteRepository;
import com.whiteboard.kartik.repository.BoardMemberRepository;
import com.whiteboard.kartik.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardInviteRepository boardInviteRepository;
    private final BoardMemberRepository boardMemberRepository;

    public BoardService(
            BoardRepository boardRepository,
            BoardMemberRepository boardMemberRepository,
            BoardInviteRepository boardInviteRepository
    ) {
        this.boardRepository = boardRepository;
        this.boardMemberRepository = boardMemberRepository;
        this.boardInviteRepository = boardInviteRepository;
    }

    public Board createBoard(CreateBoardRequest request) {
        Board board = new Board();

        board.setTitle(request.getTitle());
        board.setChatEnabled(false);
        board.setBoardCode(UUID.randomUUID().toString());

        Board savedBoard = boardRepository.save(board);

        BoardMember owner = new BoardMember();
        owner.setBoardId(savedBoard.getId());
        owner.setUserId(request.getUserId());
        owner.setRole("OWNER");

        boardMemberRepository.save(owner);

        return savedBoard;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public void deleteBoard(Long boardId, Long userId) {
        BoardMember member = boardMemberRepository
                .findByBoardIdAndUserId(boardId, userId)
                .orElse(null);

        if (member == null) {
            throw new RuntimeException("Access denied");
        }

        if (!member.getRole().equals("OWNER")) {
            throw new RuntimeException("Only owner can delete board");
        }

        boardRepository.deleteById(boardId);
    }

    public void leaveBoard(Long boardId, Long userId) {
        BoardMember member = boardMemberRepository
                .findByBoardIdAndUserId(boardId, userId)
                .orElse(null);

        if (member == null) {
            throw new RuntimeException("User is not a board member");
        }

        if (member.getRole().equals("OWNER")) {
            throw new RuntimeException("Owner cannot leave board. Owner can delete board.");
        }

        boardMemberRepository.delete(member);
    }

    public List<BoardResponse> getBoardsByUser(Long userId) {

        List<BoardMember> members =
                boardMemberRepository.findByUserId(userId);

        return members.stream().map(member -> {

            Board board = boardRepository
                    .findById(member.getBoardId())
                    .orElse(null);

            if (board == null) {
                return null;
            }

            BoardResponse response = new BoardResponse();

            response.setId(board.getId());
            response.setTitle(board.getTitle());
            response.setBoardCode(board.getBoardCode());
            response.setChatEnabled(board.isChatEnabled());
            response.setRole(member.getRole());

            return response;

        }).filter(item -> item != null).toList();
    }

    public List<BoardInvite> getPendingInvites(String email) {
        return boardInviteRepository.findByInvitedEmailAndAccepted(email, false);
    }

    public BoardMember acceptInvite(
            Long inviteId,
            AcceptInviteRequest request
    ) {
        BoardInvite invite = boardInviteRepository.findById(inviteId)
                .orElseThrow(() ->
                        new RuntimeException("Invite not found")
                );

        if (invite.isAccepted()) {
            throw new RuntimeException("Invite already accepted");
        }

        BoardMember member = new BoardMember();

        member.setBoardId(invite.getBoardId());
        member.setUserId(request.getUserId());
        member.setRole(invite.getRole());

        BoardMember savedMember = boardMemberRepository.save(member);

        invite.setAccepted(true);
        boardInviteRepository.save(invite);

        return savedMember;
    }

    public BoardResponse getBoardByBoardCode(
            String boardCode,
            Long userId
    ) {

        Board board = boardRepository
                .findByBoardCode(boardCode)
                .orElseThrow(() ->
                        new RuntimeException("Board not found")
                );

        BoardMember member =
                boardMemberRepository
                        .findByBoardIdAndUserId(
                                board.getId(),
                                userId
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Access denied")
                        );

        BoardResponse response =
                new BoardResponse();

        response.setId(board.getId());
        response.setTitle(board.getTitle());
        response.setBoardCode(board.getBoardCode());
        response.setChatEnabled(board.isChatEnabled());
        response.setRole(member.getRole());

        return response;
    }

    public BoardInvite inviteUser(
            Long boardId,
            Long ownerId,
            InviteBoardRequest request
    ) {
        BoardMember owner = boardMemberRepository
                .findByBoardIdAndUserId(boardId, ownerId)
                .orElse(null);

        if (owner == null || !owner.getRole().equals("OWNER")) {
            throw new RuntimeException("Only owner can invite users");
        }

        BoardInvite invite = new BoardInvite();

        invite.setBoardId(boardId);
        invite.setInvitedEmail(request.getInvitedEmail());
        invite.setRole(request.getRole());
        invite.setAccepted(false);

        return boardInviteRepository.save(invite);
    }

    public Board toggleChat(Long boardId, Long userId) {
        BoardMember member = boardMemberRepository
                .findByBoardIdAndUserId(boardId, userId)
                .orElse(null);

        if (member == null) {
            throw new RuntimeException("Access denied");
        }

        if (!member.getRole().equals("OWNER")) {
            throw new RuntimeException("Only owner is allowed to toggle chat");
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new RuntimeException("Board not found")
                );

        board.setChatEnabled(!board.isChatEnabled());

        return boardRepository.save(board);
    }


}
