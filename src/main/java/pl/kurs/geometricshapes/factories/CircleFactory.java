package pl.kurs.geometricshapes.factories;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.CircleDto;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.exceptions.BadNumberOfParametersExceptions;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.models.Shape;

import java.util.List;

@Component
public class CircleFactory implements Factory {
    private ModelMapper modelMapper;

    public CircleFactory(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Shape create(CreateShapeCommand createShapeCommand) {
        validParameters(createShapeCommand.getParameters());
        return modelMapper.map(createShapeCommand, Circle.class);
    }

    @Override
    public ShapeDto createShapeDTO(Shape shape) {
        return modelMapper.map(shape, CircleDto.class);
    }

    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public Shape update(Shape shape, UpdateShapeCommand updateShapeCommand) {
        Circle circle = (Circle) shape;
        validParameters(updateShapeCommand.getParameters());
        circle.setRadius(updateShapeCommand.getParameters().get(0));
        return circle;
    }

    @Override
    public void validParameters(List<Double> parameters) {
        if (parameters.size() != 1) {
            throw new BadNumberOfParametersExceptions(getType(), 1);
        }
    }
}
