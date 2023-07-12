package pl.kurs.geometricshapes.controllers;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.CircleDto;
import pl.kurs.geometricshapes.dto.RectangleDto;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.strategy.CircleStrategy;
import pl.kurs.geometricshapes.strategy.RectangleStrategy;
import pl.kurs.geometricshapes.strategy.ShapeStrategy;
import pl.kurs.geometricshapes.strategy.SquareStrategy;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shapes")
@Validated
public class ShapeController {


    private ModelMapper modelMapper;
    private Map<String, ShapeStrategy> shapeStrategies;


    @Autowired
    public ShapeController(ModelMapper modelMapper, List<ShapeStrategy> shapeStrategies) {
        this.modelMapper = modelMapper;
        this.shapeStrategies = shapeStrategies.stream()
                .collect(Collectors.toMap(ShapeStrategy::getShapeType, Function.identity()));
    }

    @PostMapping
    public ResponseEntity<ShapesDto> createShape(@RequestBody @Valid CreateShapeCommand shapeCommand) {
        String shapeType = shapeCommand.getType().toLowerCase(Locale.ROOT);
        ShapeStrategy shapeStrategy = shapeStrategies.get(shapeType);
        Shapes shapesForSave = shapeStrategy.createShape(shapeCommand);
        ShapesDto shapeDto = modelMapper.map(shapesForSave, shapeStrategy.getShapeDtoClass());
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeDto);
    }

    @PutMapping
    public ResponseEntity<ShapesDto> updateShape(@RequestBody @Valid UpdateShapeCommand command) {
        String shapeType = command.getType().toLowerCase(Locale.ROOT);
        ShapeStrategy shapeStrategy = shapeStrategies.get(shapeType);
        Shapes updatedShape = shapeStrategy.updateShape(command);
        ShapesDto shapeDto = modelMapper.map(updatedShape, shapeStrategy.getShapeDtoClass());
        return ResponseEntity.status(HttpStatus.OK).body(shapeDto);
    }

    
    @GetMapping(value = "/{type}")
    public ResponseEntity<List<ShapesDto>> getShapesByType(@PathVariable("type") String type) {
        String shapeType = type.toLowerCase(Locale.ROOT);
        ShapeStrategy shapeStrategy = shapeStrategies.get(shapeType);
        List<Shapes> shapesList = shapeStrategy.getAll();
        List<ShapesDto> shapesDtoList = shapesList.stream()
                .map(shape -> modelMapper.map(shape, shapeStrategy.getShapeDtoClass()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }


    @GetMapping(value = "/area-range")
    public ResponseEntity<List<ShapesDto>> getShapeByAreaBetweenMinAndMax(@RequestParam("areafrom") double areaFrom, @RequestParam("areato") double areaTo) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ShapeStrategy strategy : shapeStrategies.values()) {
            List<Shapes> shapes = strategy.findAllByAreaBetween(areaFrom, areaTo);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, strategy.getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }


    @GetMapping(value = "/perimeter-range")
    public ResponseEntity<List<ShapesDto>> getShapeByPerimeterBetweenMinAndMax(@RequestParam("perimeterfrom") double perimeterFrom, @RequestParam("perimeterto") double perimeterTo) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ShapeStrategy strategy : shapeStrategies.values()) {
            List<Shapes> shapes = strategy.findAllByPerimeterBetween(perimeterFrom, perimeterTo);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, strategy.getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }


    @GetMapping(value = "/created-date-range")
    public ResponseEntity<List<ShapesDto>> findAllByCreatedAtBetween(@RequestParam("createdfrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdFrom, @RequestParam("createdto") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdTo) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ShapeStrategy strategy : shapeStrategies.values()) {
            List<Shapes> shapes = strategy.findAllByCreatedAtBetween(createdFrom, createdTo);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, strategy.getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }

    @GetMapping(value = "/createdby")
    public ResponseEntity<List<ShapesDto>> findAllByCreatedBy(@RequestParam("createdby") String createdby) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ShapeStrategy strategy : shapeStrategies.values()) {
            List<Shapes> shapes = strategy.findAllByCreatedBy(createdby);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, strategy.getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }

    @GetMapping(value = "/circle-parameter-range")
    public ResponseEntity<List<CircleDto>> findAllCircleByRadiusBetween(@RequestParam("radiusfrom") double radiusFrom, @RequestParam("radiusto") double radiusTo) {
        CircleStrategy circleStrategy = (CircleStrategy) shapeStrategies.get("circle");
        List<Circle> circles = circleStrategy.findAllByRadiusBetween(radiusFrom, radiusTo);
        List<CircleDto> circleDtoList = circles.stream()
                .map(circle -> modelMapper.map(circle, CircleDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(circleDtoList);
    }


    @GetMapping(value = "/square-parameter-range")
    public ResponseEntity<List<SquareDto>> findAllSquareByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo) {
        SquareStrategy squareStrategy = (SquareStrategy) shapeStrategies.get("square");
        List<Square> squares = squareStrategy.findAllByWidthBetween(widthFrom, widthTo);
        List<SquareDto> squareDtoList = squares.stream()
                .map(square -> modelMapper.map(square, SquareDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(squareDtoList);
    }

    @GetMapping(value = "/rectangle-parameter-range")
    public ResponseEntity<List<RectangleDto>> findAllRectangleByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo, @RequestParam("lengthfrom") double lengthFrom, @RequestParam("lengthto") double lengthTo) {
        RectangleStrategy rectangleStrategy = (RectangleStrategy) shapeStrategies.get("rectangle");
        List<Rectangle> rectangles = rectangleStrategy.findAllByWidthBetweenAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo);
        List<RectangleDto> rectangleDtoList = rectangles.stream()
                .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(rectangleDtoList);
    }
}

