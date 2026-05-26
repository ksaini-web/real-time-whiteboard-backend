package com.whiteboard.kartik.repository;

import com.whiteboard.kartik.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShapeRepository extends JpaRepository <Shape, Long> {

    List<Shape> findByBoardId(Long boardId);


}