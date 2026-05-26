package com.whiteboard.kartik.repository;

import com.whiteboard.kartik.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {

    Optional<Action> findTopByBoardIdAndUndoneFalseOrderByIdDesc(Long boardId);

    Optional<Action> findTopByBoardIdAndUndoneTrueOrderByIdDesc(Long boardId);
}