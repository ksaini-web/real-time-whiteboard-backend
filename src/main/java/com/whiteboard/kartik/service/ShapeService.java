package com.whiteboard.kartik.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiteboard.kartik.model.Action;
import com.whiteboard.kartik.model.Board;
import com.whiteboard.kartik.model.Shape;
import com.whiteboard.kartik.repository.ActionRepository;
import com.whiteboard.kartik.repository.ShapeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final ActionRepository actionRepository;

    public ShapeService(
            ShapeRepository shapeRepository,
            ActionRepository actionRepository
    ) {
        this.shapeRepository = shapeRepository;
        this.actionRepository = actionRepository;
    }

    public Shape saveShape(Shape shape) {
        Shape savedShape = shapeRepository.save(shape);

        Action action = new Action();
        action.setBoardId(savedShape.getBoardId());
        action.setActionType("CREATE");
        action.setShapeId(savedShape.getId());
        action.setShapeType(savedShape.getType());
        action.setNewData(savedShape.getShapeData());
        action.setUndone(false);

        actionRepository.save(action);

        return savedShape;
    }

    public List<Shape> getShapesbyBoardId(Long boardId) {
        return shapeRepository.findByBoardId(boardId);
    }

    public void deleteShape(Long id) {
        Shape existingShape =
                shapeRepository.findById(id).orElse(null);

        if (existingShape == null) {
            return;
        }

        Action action = new Action();
        action.setBoardId(existingShape.getBoardId());
        action.setActionType("DELETE");
        action.setShapeId(existingShape.getId());
        action.setShapeType(existingShape.getType());
        action.setOldData(existingShape.getShapeData());
        action.setUndone(false);

        actionRepository.save(action);

        shapeRepository.deleteById(id);
    }

    public void deleteAllShapesByBoard(Long boardId) throws Exception {
        List<Shape> shapes = shapeRepository.findByBoardId(boardId);

        Action action = new Action();
        action.setBoardId(boardId);
        action.setActionType("CLEAR");
        action.setOldData(
                new ObjectMapper().writeValueAsString(shapes)
        );
        action.setUndone(false);

        actionRepository.save(action);

        shapeRepository.deleteAll(shapes);
    }
    public Shape updateShape(Long id, Shape shape) {
        Shape existingShape =
                shapeRepository.findById(id).orElse(null);

        if (existingShape == null) {
            return null;
        }

        String oldData = existingShape.getShapeData();

        existingShape.setType(shape.getType());
        existingShape.setShapeData(shape.getShapeData());

        Shape updatedShape = shapeRepository.save(existingShape);

        Action action = new Action();
        action.setBoardId(updatedShape.getBoardId());
        action.setActionType("MOVE");
        action.setShapeId(updatedShape.getId());
        action.setShapeType(updatedShape.getType());
        action.setOldData(oldData);
        action.setNewData(updatedShape.getShapeData());
        action.setUndone(false);

        actionRepository.save(action);

        return updatedShape;
    }
}