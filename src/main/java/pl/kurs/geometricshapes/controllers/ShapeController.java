package pl.kurs.geometricshapes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.dto.StatusDto;
import pl.kurs.geometricshapes.models.Shape;
import pl.kurs.geometricshapes.services.ShapeService;

import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
@RequestMapping("/api/v1/shapes")
public class ShapeController {
    private final ShapeService shapeService;

    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping
    public ResponseEntity<ShapeDto> create(@RequestBody @Valid CreateShapeCommand createShapeCommand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeService.create(createShapeCommand));
    }

    @GetMapping
    public ResponseEntity<List<ShapeDto>> findAll(@RequestParam(required = false) Map<String, String> queryParams) {
        return ResponseEntity.status(HttpStatus.OK).body(shapeService.getShapes(queryParams));
    }

    @PutMapping
    public ResponseEntity<Shape> updateShape(@RequestBody @Valid UpdateShapeCommand updateShapeCommand) {
        return ResponseEntity.status(HttpStatus.OK).body(shapeService.update(updateShapeCommand));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StatusDto> deleteShape(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(shapeService.delete(id));
    }
}