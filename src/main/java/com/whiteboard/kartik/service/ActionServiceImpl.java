package com.whiteboard.kartik.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whiteboard.kartik.model.Action;
import com.whiteboard.kartik.model.Shape;
import com.whiteboard.kartik.repository.ActionRepository;
import com.whiteboard.kartik.repository.ShapeRepository;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;
    private final ShapeRepository shapeRepository;

    public ActionServiceImpl(
            ActionRepository actionRepository,
            ShapeRepository shapeRepository
    ) {
        this.actionRepository = actionRepository;
        this.shapeRepository = shapeRepository;
    }

    @Override
    public Action saveAction(Action action) {
        return actionRepository.save(action);
    }

    @Override
    public void undo(Long boardId) {
        Action lastAction =
                actionRepository
                        .findTopByBoardIdAndUndoneFalseOrderByIdDesc(boardId)
                        .orElse(null);

        if (lastAction == null) {
            return;
        }

        if (lastAction.getActionType().equals("CREATE")) {
            shapeRepository.deleteById(lastAction.getShapeId());
        }

        if (lastAction.getActionType().equals("MOVE")) {
            Shape shape =
                    shapeRepository.findById(lastAction.getShapeId())
                            .orElse(null);

            if (shape != null) {
                shape.setShapeData(lastAction.getOldData());
                shapeRepository.save(shape);
            }
        }

        if (lastAction.getActionType().equals("DELETE")) {
            Shape shape = new Shape();

            shape.setBoardId(lastAction.getBoardId());
            shape.setType(lastAction.getShapeType());
            shape.setShapeData(lastAction.getOldData());

            shapeRepository.save(shape);
        }

        if (lastAction.getActionType().equals("CLEAR")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                Shape[] shapes =
                        objectMapper.readValue(
                                lastAction.getOldData(),
                                Shape[].class
                        );

                for (Shape shape : shapes) {
                    shape.setId(null);
                    shapeRepository.save(shape);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        lastAction.setUndone(true);
        actionRepository.save(lastAction);
    }

    @Override
    public void redo(Long boardId) {
        Action lastUndoneAction =
                actionRepository
                        .findTopByBoardIdAndUndoneTrueOrderByIdDesc(boardId)
                        .orElse(null);

        if (lastUndoneAction == null) {
            return;
        }

        if (lastUndoneAction.getActionType().equals("CREATE")) {
            Shape shape = new Shape();

            shape.setBoardId(lastUndoneAction.getBoardId());
            shape.setType(lastUndoneAction.getShapeType());
            shape.setShapeData(lastUndoneAction.getNewData());

            shapeRepository.save(shape);
        }

        if (lastUndoneAction.getActionType().equals("MOVE")) {
            Shape shape =
                    shapeRepository
                            .findById(lastUndoneAction.getShapeId())
                            .orElse(null);

            if (shape != null) {
                shape.setShapeData(lastUndoneAction.getNewData());
                shapeRepository.save(shape);
            }
        }

        if (lastUndoneAction.getActionType().equals("DELETE")) {
            shapeRepository.deleteById(
                    lastUndoneAction.getShapeId()
            );
        }

        if (lastUndoneAction.getActionType().equals("CLEAR")) {
            shapeRepository.deleteAll();
        }

        lastUndoneAction.setUndone(false);
        actionRepository.save(lastUndoneAction);
    }
}