package com.whiteboard.kartik.repository;

import com.whiteboard.kartik.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {


    Optional<Board> findByBoardCode(String boardCode);
}
