package com.whiteboard.kartik.service;

import com.whiteboard.kartik.model.Action;

public interface ActionService {

    Action saveAction(Action action);

    void undo(Long boardId);

    void redo(Long boardId);

}