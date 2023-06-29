package pl.kurs.geometricshapes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.config.ModelMapperConfig;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.models.*;
import pl.kurs.geometricshapes.services.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shapes")
public class ShapeController {


    private ModelMapperConfig modelMapperConfig;
    private final Map<ShapeType, ShapeManagementServices> shapeServices;

    public ShapeController(ModelMapperConfig modelMapperConfig, CircleManagementServices circleServices, RectangleManagementServices rectangleServices, SquareManagementServices squareServices) {
        this.modelMapperConfig = modelMapperConfig;
        shapeServices = new HashMap<>();
        shapeServices.put(ShapeType.CIRCLE, circleServices);
        shapeServices.put(ShapeType.RECTANGLE, rectangleServices);
        shapeServices.put(ShapeType.SQUARE, squareServices);
    }


    @PostMapping
    public ResponseEntity<ShapesDto> createShape(@RequestBody CreateShapeCommand shapeCommand) {
        ShapeType shapeType = shapeCommand.getType();
        ShapeManagementServices shapeManagementServices = shapeServices.get(shapeType);
        Shapes shapeForSave = modelMapperConfig.modelMapper().map(shapeCommand, shapeType.getShapeClass());
        shapeManagementServices.add(shapeForSave);
        ShapesDto shapeDto = modelMapperConfig.modelMapper().map(shapeForSave, shapeType.getShapeDtoClass());
        return ResponseEntity.status(HttpStatus.CREATED).body(shapeDto);
    }

//    @GetMapping(value = "/{type}")
//    public ResponseEntity<List<?>> getShapeByType(@PathVariable("type") String type) {
//        ShapeType shapeType = ShapeType.valueOf(type.toUpperCase());
//        if (shapeType == ShapeType.SQUARE) {
//            List<SquareDto> squareDtoList = squareManagementServices.getAll().stream()
//                    .map(square -> modelMapper.map(square, SquareDto.class))
//                    .collect(Collectors.toList());
//            return ResponseEntity.status(HttpStatus.OK).body(squareDtoList);
//        } else if (shapeType == ShapeType.RECTANGLE) {
//            List<RectangleDto> rectangleDtoList = rectangleManagementServices.getAll().stream()
//                    .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
//                    .collect(Collectors.toList());
//            return ResponseEntity.status(HttpStatus.OK).body(rectangleDtoList);
//        } else if (shapeType == ShapeType.CIRCLE) {
//            List<CircleDto> circleDtoList = circleManagementServices.getAll().stream()
//                    .map(circle -> modelMapper.map(circle, CircleDto.class))
//                    .collect(Collectors.toList());
//            return ResponseEntity.status(HttpStatus.OK).body(circleDtoList);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
//
//    @GetMapping(value = "/area-range") // http://localhost:8080/shapes/area-range?areafrom=100.0&areato=400.0
//    public ResponseEntity<List<ShapesDto>> getShapeByAreaBetweenMinAndMax(@RequestParam("areafrom") double areaFrom, @RequestParam("areato") double areaTo) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//
//        List<SquareDto> squareDtoList = squareManagementServices.findAllByAreaBetween(areaFrom, areaTo).stream()
//                .map(square -> modelMapper.map(square, SquareDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(squareDtoList);
//
//        List<RectangleDto> rectangleDtoList = rectangleManagementServices.findAllByAreaBetween(areaFrom, areaTo).stream()
//                .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(rectangleDtoList);
//
//        List<CircleDto> circleDtoList = circleManagementServices.findAllByAreaBetween(areaFrom, areaTo).stream()
//                .map(circle -> modelMapper.map(circle, CircleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(circleDtoList);
//
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//    @GetMapping(value = "/perimeter-range") //http://localhost:8080/shapes/perimeter-range?perimeterfrom=0.0&perimeterto=100.0
//    public ResponseEntity<List<ShapesDto>> getShapeByPerimeterBetweenMinAndMax(@RequestParam("perimeterfrom") double perimeterFrom, @RequestParam("perimeterto") double perimeterTo) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//
//        List<SquareDto> squareDtoList = squareManagementServices.findAllByPerimeterBetween(perimeterFrom, perimeterTo).stream()
//                .map(square -> modelMapper.map(square, SquareDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(squareDtoList);
//
//        List<RectangleDto> rectangleDtoList = rectangleManagementServices.findAllByPerimeterBetween(perimeterFrom, perimeterTo).stream()
//                .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(rectangleDtoList);
//
//        List<CircleDto> circleDtoList = circleManagementServices.findAllByPerimeterBetween(perimeterFrom, perimeterTo).stream()
//                .map(circle -> modelMapper.map(circle, CircleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(circleDtoList);
//
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//    @GetMapping(value = "/created-date-range") //http://localhost:8080/shapes/created-date-range?createdfrom=27.06.2023&createdto=27.06.2023
//    public ResponseEntity<List<ShapesDto>> findAllByCreatedAtBetween(@RequestParam("createdfrom") LocalDate createdFrom, @RequestParam("createdto") LocalDate createdTo) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//
//        List<SquareDto> squareDtoList = squareManagementServices.findAllByCreatedAtBetween(createdFrom, createdTo).stream()
//                .map(square -> modelMapper.map(square, SquareDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(squareDtoList);
//
//        List<RectangleDto> rectangleDtoList = rectangleManagementServices.findAllByCreatedAtBetween(createdFrom, createdTo).stream()
//                .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(rectangleDtoList);
//
//        List<CircleDto> circleDtoList = circleManagementServices.findAllByCreatedAtBetween(createdFrom, createdTo).stream()
//                .map(circle -> modelMapper.map(circle, CircleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(circleDtoList);
//
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//    @GetMapping(value = "/createdby") //http://localhost:8080/shapes/createdby?createdby=roko
//    public ResponseEntity<List<ShapesDto>> findAllByCreatedBy(@RequestParam("createdby") String createdby) {
//        List<ShapesDto> shapesDtoList = new ArrayList<>();
//
//        List<SquareDto> squareDtoList = squareManagementServices.findAllByCreatedBy(createdby).stream()
//                .map(square -> modelMapper.map(square, SquareDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(squareDtoList);
//
//        List<RectangleDto> rectangleDtoList = rectangleManagementServices.findAllByCreatedBy(createdby).stream()
//                .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(rectangleDtoList);
//
//        List<CircleDto> circleDtoList = circleManagementServices.findAllByCreatedBy(createdby).stream()
//                .map(circle -> modelMapper.map(circle, CircleDto.class))
//                .collect(Collectors.toList());
//        shapesDtoList.addAll(circleDtoList);
//
//        return ResponseEntity.status(HttpStatus.OK).body(shapesDtoList);
//    }
//
//
//    @GetMapping(value = "/square-parameter-range") //http://localhost:8080/shapes/square-parameter-range?widthfrom=10.0&widthto=20.0
//    ResponseEntity<List<SquareDto>> findAllSquareByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo) {
//        List<SquareDto> squareDtoList = squareManagementServices.findAllByWidthBetween(widthFrom, widthTo).stream()
//                .map(square -> modelMapper.map(square, SquareDto.class))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(squareDtoList);
//    }
//
//    @GetMapping(value = "/rectangle-parameter-range") //http://localhost:8080/shapes/rectangle-parameter-range?widthfrom=10.0&widthto=20.0&lengthfrom=10.0&lengthto=20.0
//    ResponseEntity<List<RectangleDto>> findAllRectangleByWidthBetween(@RequestParam("widthfrom") double widthFrom, @RequestParam("widthto") double widthTo, @RequestParam("lengthfrom") double lengthFrom, @RequestParam("lengthto") double lengthTo) {
//        List<RectangleDto> rectangleDtoList = rectangleManagementServices.findAllByWidthBetweenAndAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo).stream()
//                .map(rectangle -> modelMapper.map(rectangle, RectangleDto.class))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(rectangleDtoList);
//    }
//
//    @GetMapping(value = "/circle-parameter-range") //http://localhost:8080/shapes/circle-parameter-range?radiusfrom=10.0&radiusto=20.0
//    ResponseEntity<List<CircleDto>> findAllCircleByWidthBetween(@RequestParam("radiusfrom") double radiusFrom, @RequestParam("radiusto") double radiusTo) {
//        List<CircleDto> circleDtoList = circleManagementServices.findAllByRadiusBetween(radiusFrom, radiusTo).stream()
//                .map(circle -> modelMapper.map(circle, CircleDto.class))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(circleDtoList);
//    }

}
