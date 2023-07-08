package pl.kurs.geometricshapes.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.models.*;
import pl.kurs.geometricshapes.services.*;


import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shapes")
@Validated
public class ShapeController<T extends Identificationable> {


    private ModelMapper modelMapper;
    private ShapesService<T> shapesService;

    public ShapeController(ModelMapper modelMapper, ShapesService<T> shapesService) {
        this.modelMapper = modelMapper;
        this.shapesService = shapesService;
    }

    @PostMapping
    public ResponseEntity<ShapesDto> createShape(@RequestBody CreateShapeCommand shapeCommand) {
        ShapeType shapeType = shapeCommand.getType();
        T shapeForSave = modelMapper.map(shapeCommand, shapeType.getShapeClass());
        shapesService.add(shapeForSave);
        ShapesDto shapeDto = modelMapper.map(shapeForSave, shapeType.getShapeDtoClass());
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeDto);
    }


    @GetMapping(value = "/{type}")
    public ResponseEntity<List<ShapesDto>> getShapesByType(@PathVariable("type") String type) {
        ShapeType shapeType = ShapeType.valueOf(type.toUpperCase());
        List<T> shapes = shapesService.getAll();
        List<ShapesDto> shapesDtoList = shapes.stream()
                .map(shape -> modelMapper.map(shape, shapeType.getShapeDtoClass()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }


//    @GetMapping(value = "/area-range")
//    public ResponseEntity<List<ShapesDto>> getShapeByAreaBetweenMinAndMax(@RequestParam("areafrom") double areaFrom, @RequestParam("areato") double areaTo) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//        List<Shapes> shapes = shapesService.findAllByAreaBetween(areaFrom, areaTo);
//        List<ShapesDto> shapesDtos = shapes.stream()
//                .map(shape -> modelMapper.map(shape, shapeType.getShapeDtoClass()))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(shapesDtos);
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//
//    @GetMapping(value = "/perimeter-range")
//    public ResponseEntity<List<ShapesDto>> getShapeByPerimeterBetweenMinAndMax(@RequestParam("perimeterfrom") double perimeterFrom, @RequestParam("perimeterto") double perimeterTo) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//        for (Map.Entry<ShapeType, GenericManagementServices> entry : shapeServices.entrySet()) {
//            ShapeType shapeType = entry.getKey();
//            GenericManagementServices genericManagementServices = entry.getValue();
//            List<Shapes> shapes = genericManagementServices.findAllByPerimeterBetween(perimeterFrom, perimeterTo);
//            List<ShapesDto> shapesDtos = shapes.stream()
//                    .map(shape -> modelMapperConfig.modelMapper().map(shape, shapeType.getShapeDtoClass()))
//                    .collect(Collectors.toList());
//            shapesDtoList.addAll(shapesDtos);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//    @GetMapping(value = "/created-date-range")
//    public ResponseEntity<List<ShapesDto>> findAllByCreatedAtBetween(@RequestParam("createdfrom") LocalDate createdFrom, @RequestParam("createdto") LocalDate createdTo) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//        for (Map.Entry<ShapeType, GenericManagementServices> entry : shapeServices.entrySet()) {
//            ShapeType shapeType = entry.getKey();
//            GenericManagementServices genericManagementServices = entry.getValue();
//            List<Shapes> shapes = genericManagementServices.findAllByCreatedAtBetween(createdFrom, createdTo);
//            List<ShapesDto> shapesDtos = shapes.stream()
//                    .map(shape -> modelMapperConfig.modelMapper().map(shape, shapeType.getShapeDtoClass()))
//                    .collect(Collectors.toList());
//            shapesDtoList.addAll(shapesDtos);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//    @GetMapping(value = "/createdby")
//    public ResponseEntity<List<ShapesDto>> findAllByCreatedBy(@RequestParam("createdby") String createdby) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//        for (Map.Entry<ShapeType, GenericManagementServices> entry : shapeServices.entrySet()) {
//            ShapeType shapeType = entry.getKey();
//            GenericManagementServices genericManagementServices = entry.getValue();
//            List<Shapes> shapes = genericManagementServices.findAllByCreatedBy(createdby);
//            List<ShapesDto> shapesDtos = shapes.stream()
//                    .map(shape -> modelMapperConfig.modelMapper().map(shape, shapeType.getShapeDtoClass()))
//                    .collect(Collectors.toList());
//            shapesDtoList.addAll(shapesDtos);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//    @GetMapping(value = "/radius")
//    public ResponseEntity<List<CircleDto>> findAllCircleByRadiusBetween(@RequestParam("radiusfrom") double radiusFrom, @RequestParam("radiusto") double radiusTo) {
//        CircleManagementServices circleManagementServices = (CircleManagementServices) shapeServices.get(ShapeType.CIRCLE);
//        List<Circle> circles = circleManagementServices.findAllByRadiusBetween(radiusFrom, radiusTo);
//        List<CircleDto> circleDtoList = circles.stream()
//                .map(circle -> modelMapperConfig.modelMapper().map(circle, CircleDto.class))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(circleDtoList);
//    }
//
//    @GetMapping(value = "/width")
//    public ResponseEntity<List<SquareDto>> findAllSquareByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo) {
//        SquareManagementServices squareManagementServices = (SquareManagementServices) shapeServices.get(ShapeType.SQUARE);
//        List<Square> squares = squareManagementServices.findAllByWidthBetween(widthFrom, widthTo);
//        List<SquareDto> squareDtoList = squares.stream()
//                .map(square -> modelMapperConfig.modelMapper().map(square, SquareDto.class))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(squareDtoList);
//    }
//
//    @GetMapping(value = "width-and-length")
//    public ResponseEntity<List<RectangleDto>> findAllRectangleByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo, @RequestParam("lengthfrom") double lengthFrom, @RequestParam("lengthto") double lengthTo) {
//        RectangleManagementServices rectangleManagementServices = (RectangleManagementServices) shapeServices.get(ShapeType.RECTANGLE);
//        List<Rectangle> rectangles = rectangleManagementServices.findAllByWidthBetweenAndAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo);
//        List<RectangleDto> rectangleDtoList = rectangles.stream()
//                .map(rectangle -> modelMapperConfig.modelMapper().map(rectangle, RectangleDto.class))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(rectangleDtoList);
//    }
}

