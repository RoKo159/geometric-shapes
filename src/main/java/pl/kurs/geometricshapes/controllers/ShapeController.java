package pl.kurs.geometricshapes.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.dto.CircleDto;
import pl.kurs.geometricshapes.dto.RectangleDto;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;
import pl.kurs.geometricshapes.models.*;
import pl.kurs.geometricshapes.repository.CircleRepository;
import pl.kurs.geometricshapes.repository.RectangleRepository;
import pl.kurs.geometricshapes.repository.SquareRepository;
import pl.kurs.geometricshapes.services.*;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shapes")
@Validated
public class ShapeController {


    private ModelMapper modelMapper;
    private ManagementServiceFactoryProvider factoryProvider;

    public ShapeController(ModelMapper modelMapper, ManagementServiceFactoryProvider factoryProvider) {
        this.modelMapper = modelMapper;
        this.factoryProvider = factoryProvider;
    }

    @PostMapping
    public ResponseEntity<ShapesDto> createShape(@RequestBody @Valid CreateShapeCommand shapeCommand) {
        ShapeType shapeType = ShapeType.valueOf(shapeCommand.getType().toUpperCase(Locale.ROOT));
        Shapes shapeForSave = modelMapper.map(shapeCommand, shapeType.getShapeClass());
        factoryProvider.getFactory(shapeType).createManagementService().add(shapeForSave);
        ShapesDto shapeDto = modelMapper.map(shapeForSave, shapeType.getShapeDtoClass());
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeDto);
    }


    @GetMapping(value = "/{type}")
    public ResponseEntity<List<ShapesDto>> getShapesByType(@PathVariable("type") String type) {
        List<Identificationable> shapesList = factoryProvider.getFactory(ShapeType.valueOf(type.toUpperCase(Locale.ROOT))).createManagementService().getAll();
        List<ShapesDto> shapesDtoList = shapesList.stream()
                .map(shape -> modelMapper.map(shape, ShapeType.valueOf(type.toUpperCase(Locale.ROOT)).getShapeDtoClass()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }


    @GetMapping(value = "/area-range")
    public ResponseEntity<List<ShapesDto>> getShapeByAreaBetweenMinAndMax(@RequestParam("areafrom") double areaFrom, @RequestParam("areato") double areaTo) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ManagementServiceFactory<?, ?> factory : factoryProvider.getAllFactories()) {
            List<?> shapes = factory.createManagementService().findAllByAreaBetween(areaFrom, areaTo);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, ShapeType.valueOf(shape.getClass().getSimpleName().toUpperCase()).getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }


    @GetMapping(value = "/perimeter-range")
    public ResponseEntity<List<ShapesDto>> getShapeByPerimeterBetweenMinAndMax(@RequestParam("perimeterfrom") double perimeterFrom, @RequestParam("perimeterto") double perimeterTo) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ManagementServiceFactory<?, ?> factory : factoryProvider.getAllFactories()) {
            List<?> shapes = factory.createManagementService().findAllByPerimeterBetween(perimeterFrom, perimeterTo);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, ShapeType.valueOf(shape.getClass().getSimpleName().toUpperCase()).getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }

    @GetMapping(value = "/created-date-range")
    public ResponseEntity<List<ShapesDto>> findAllByCreatedAtBetween(@RequestParam("createdfrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdFrom, @RequestParam("createdto") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdTo) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ManagementServiceFactory<?, ?> factory : factoryProvider.getAllFactories()) {
            List<?> shapes = factory.createManagementService().findAllByCreatedAtBetween(createdFrom, createdTo);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, ShapeType.valueOf(shape.getClass().getSimpleName().toUpperCase()).getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }

    @GetMapping(value = "/createdby")
    public ResponseEntity<List<ShapesDto>> findAllByCreatedBy(@RequestParam("createdby") String createdby) {
        List<ShapesDto> shapesDtoList = new ArrayList<>();
        for (ManagementServiceFactory<?, ?> factory : factoryProvider.getAllFactories()) {
            List<?> shapes = factory.createManagementService().findAllByCreatedBy(createdby);
            List<ShapesDto> shapesDtos = shapes.stream()
                    .map(shape -> modelMapper.map(shape, ShapeType.valueOf(shape.getClass().getSimpleName().toUpperCase()).getShapeDtoClass()))
                    .collect(Collectors.toList());
            shapesDtoList.addAll(shapesDtos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
    }

    @GetMapping(value = "/circle-parameter-range")
    public ResponseEntity<List<CircleDto>> findAllCircleByRadiusBetween(@RequestParam("radiusfrom") double radiusFrom, @RequestParam("radiusto") double radiusTo) {
        ManagementServiceFactory<Circle, CircleRepository> factory = factoryProvider.getFactory(ShapeType.CIRCLE);
        CircleManagementServices circleManagementServices = (CircleManagementServices) factory.createManagementService();
        List<Circle> circles = circleManagementServices.findAllByRadiusBetween(radiusFrom, radiusTo);
        List<CircleDto> circleDtoList = circles.stream()
                .map(circle -> modelMapper.map(circle, CircleDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(circleDtoList);
    }

    @GetMapping(value = "/square-parameter-range")
    public ResponseEntity<List<SquareDto>> findAllSquareByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo) {
        ManagementServiceFactory<Square, SquareRepository> factory = factoryProvider.getFactory(ShapeType.SQUARE);
        SquareManagementServices squareManagementServices = (SquareManagementServices) factory.createManagementService();
        List<Square> squares = squareManagementServices.findAllByWidthBetween(widthFrom, widthTo);
        List<SquareDto> squareDtoList = squares.stream()
                .map(square -> modelMapper.map(square, SquareDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(squareDtoList);
    }

    @GetMapping(value = "/rectangle-parameter-range")
    public ResponseEntity<List<RectangleDto>> findAllRectangleByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo, @RequestParam("lengthfrom") double lengthFrom, @RequestParam("lengthto") double lengthTo) {
        ManagementServiceFactory<Rectangle, RectangleRepository> factory = factoryProvider.getFactory(ShapeType.RECTANGLE);
        RectangleManagementServices rectangleManagementServices = (RectangleManagementServices) factory.createManagementService();
        List<Rectangle> rectangles = rectangleManagementServices.findAllByWidthBetweenAndAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo);
        List<RectangleDto> rectangleDtoList = rectangles.stream()
                .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(rectangleDtoList);
    }
}

