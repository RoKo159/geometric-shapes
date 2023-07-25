package pl.kurs.geometricshapes.controllers;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/parameters")
    public ResponseEntity<List<ShapesDto>> getShapesByFilteredParameters(@RequestParam Map<String,String> allParams) {
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

    //todo napisać metodę kasującą, poprawić walidację, obsłużyć wyjątki kiedy nie znajdzie odpowiedniego kształtu,
    // napiać do tego odpowienie testy, poprawić to żeby area i perimeter nie były w modelu, upewnić się że na 100% nie trzeba modyfikować kodu by dodać nowy kształt,
    // obejrzec filmik o liquidbase i upewnić sie ze jest dobrze zrobione, sprawdzić nazewnictwo metod referencji itd,
    // sprawdzić o co dokłanie chodzi z wersionowaniem, zrobić to Sql view, żeby była w bazie danych tabela widok, który ma id figury oraz jej pole i obwód wiliczane z jej cech

}