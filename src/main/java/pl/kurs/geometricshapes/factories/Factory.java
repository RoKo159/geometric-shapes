package pl.kurs.geometricshapes.factories;

import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.models.Shape;

import java.util.List;

public interface Factory {
    Shape create(CreateShapeCommand createShapeCommand);

    ShapeDto createShapeDTO(Shape shape);

    String getType();

    Shape update(Shape shape, UpdateShapeCommand updateShapeCommand);

    void validParameters(List<Double> parameters);
}
