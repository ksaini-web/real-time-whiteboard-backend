package com.whiteboard.kartik.controller;


import com.whiteboard.kartik.model.Shape;
import com.whiteboard.kartik.service.ShapeService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/shapes")


public class ShapeController {

    private final ShapeService shapeService;

    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping
    public  Shape saveShape(@RequestBody Shape shape){

        return shapeService.saveShape(shape);
    }

    @GetMapping("/{boardId}")
    public List<Shape> getShapes(@PathVariable Long boardId){


        return shapeService.getShapesbyBoardId(boardId);

    }

    @DeleteMapping("/{id}")
    public void deleteShape(@PathVariable Long id){

        shapeService.deleteShape(id);

    }

    @PutMapping("/{id}")
    public Shape updateShape(@RequestBody Shape shape, @PathVariable Long id){
        return shapeService.updateShape(id,shape);

    }

    @DeleteMapping("/board/{boardId}/clear")
    public void clearBoard(@PathVariable Long boardId) throws Exception {
        shapeService.deleteAllShapesByBoard(boardId);
    }








}
