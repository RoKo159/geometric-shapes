package pl.kurs.geometricshapes.factories;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.RectangleDto;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.exceptions.BadNumberOfParametersExceptions;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.models.Shape;

import java.util.List;

@Component
public class RectangleFactory implements Factory {
    private ModelMapper modelMapper;

    public RectangleFactory(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Shape create(CreateShapeCommand createShapeCommand) {
        validParameters(createShapeCommand.getParameters());
        return modelMapper.map(createShapeCommand, Rectangle.class);
    }

    @Override
    public ShapeDto createShapeDTO(Shape shape) {
        return modelMapper.map(shape, RectangleDto.class);
    }

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public Shape update(Shape shape, UpdateShapeCommand updateShapeCommand) {
        Rectangle rectangle = (Rectangle) shape;
        validParameters(updateShapeCommand.getParameters());
        rectangle.setWidth(updateShapeCommand.getParameters().get(0));
        rectangle.setLength(updateShapeCommand.getParameters().get(1));
        return rectangle;
    }

    @Override
    public void validParameters(List<Double> parameters) {
        if (parameters.size() != 2) {
            throw new BadNumberOfParametersExceptions(getType(), 2);
        }
    }


}
