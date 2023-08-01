package pl.kurs.geometricshapes.services;

import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.exceptions.NoShapeTypeException;
import pl.kurs.geometricshapes.factories.Factory;
import pl.kurs.geometricshapes.models.Shape;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShapeFactoryService {
    private final Map<String, Factory> factoryMap;

    public ShapeFactoryService(List<Factory> factories) {
        factoryMap = factories.stream()
                .collect(Collectors.toMap(Factory::getType, Function.identity()));
    }

    public Shape create(CreateShapeCommand createShapeCommand) {
        return Optional.ofNullable(factoryMap.get(createShapeCommand.getType()))
                .orElseThrow(() -> new NoShapeTypeException(createShapeCommand.getType()))
                .create(createShapeCommand);
    }

    public ShapeDto createShapeDto(Shape shape) {
        return Optional.ofNullable(factoryMap.get(shape.getType()))
                .orElseThrow(() -> new NoShapeTypeException(shape.getType()))
                .createShapeDTO(shape);
    }

    public Shape update(Shape shapeToUpdate, UpdateShapeCommand updateShapeCommand) {
        return Optional.ofNullable(factoryMap.get(updateShapeCommand.getType()))
                .orElseThrow(() -> new NoShapeTypeException(updateShapeCommand.getType()))
                .update(shapeToUpdate, updateShapeCommand);
    }
}
