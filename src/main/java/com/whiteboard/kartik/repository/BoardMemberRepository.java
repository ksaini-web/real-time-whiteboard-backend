package com.whiteboard.kartik.repository;

import com.whiteboard.kartik.model.Board;
import com.whiteboard.kartik.model.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardMemberRepository
        extends JpaRepository<BoardMember, Long> {

    List<BoardMember> findByBoardId(
            Long boardId
    );

    Optional<BoardMember> findByBoardIdAndUserId(
            Long boardId,
            Long userId
    );

    @Query("""
        SELECT b
        FROM Board b
        JOIN BoardMember bm
        ON b.id = bm.boardId
        WHERE bm.userId = :userId
    """)
    List<Board> findBoardsByUserId(
            Long userId
    );

    List<BoardMember> findByUserId(Long userId);
}