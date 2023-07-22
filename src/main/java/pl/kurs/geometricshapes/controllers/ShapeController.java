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
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.strategy.ShapeStrategy;


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


    @GetMapping(value = "/parameters")
    public ResponseEntity<List<ShapesDto>> getShapeByAreaBetweenMinAndMax(@RequestParam Map<String,String> allParams) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ShapeStrategy strategy : shapeStrategies.values()) {
            List<Shapes> shapes = strategy.getShapesByFilteredParameters(allParams);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, strategy.getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }


//    @GetMapping(value = "/parameters")
//    public ResponseEntity<List<ShapesDto>> getShapeByAreaBetweenMinAndMax(
//            @RequestParam(required = false, name = "type") String type,
//            @RequestParam(required = false, name = "perimeterfrom") Double perimeterFrom,
//            @RequestParam(required = false, name = "perimeterto") Double perimeterTo,
//            @RequestParam(required = false, name = "areafrom") Double areaFrom,
//            @RequestParam(required = false, name = "areato") Double areaTo,
//            @RequestParam(required = false, name = "datefrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
//            @RequestParam(required = false, name = "dateto") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
//            @RequestParam(required = false, name = "createdby") String createdBy,
//            @RequestParam(required = false, name = "radiusfrom") Double radiusFrom,
//            @RequestParam(required = false, name = "radiusto") Double radiusTo,
//            @RequestParam(required = false, name = "widthfrom") Double widthFrom,
//            @RequestParam(required = false, name = "widthto") Double widthTo,
//            @RequestParam(required = false, name = "lengthfrom") Double lengthFrom,
//            @RequestParam(required = false, name = "lengthto") Double lengthTo
//    ) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//        for (ShapeStrategy strategy : shapeStrategies.values()) {
//            List<Shapes> shapes = strategy.getShapesByFilteredParameters(
//                    type, createdBy, dateFrom, dateTo, areaFrom, areaTo,
//                    perimeterFrom, perimeterTo, widthFrom, widthTo, lengthFrom,
//                    lengthTo, radiusFrom, radiusTo
//            );
//            List<ShapesDto> shapesDtos = shapes.stream()
//                    .map(shape -> modelMapper.map(shape, strategy.getShapeDtoClass()))
//                    .collect(Collectors.toList());
//            shapesDtoList.addAll(shapesDtos);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }

}