package pl.kurs.geometricshapes.factories;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.dto.SquareDto;
import pl.kurs.geometricshapes.exceptions.BadNumberOfParametersExceptions;
import pl.kurs.geometricshapes.models.Shape;
import pl.kurs.geometricshapes.models.Square;

import java.util.List;

@Component
public class SquareFactory implements Factory {
    private ModelMapper modelMapper;

    public SquareFactory(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Shape create(CreateShapeCommand createShapeCommand) {
        validParameters(createShapeCommand.getParameters());
        return modelMapper.map(createShapeCommand, Square.class);
    }

    @Override
    public ShapeDto createShapeDTO(Shape shape) {
        return modelMapper.map(shape, SquareDto.class);
    }

    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public Shape update(Shape shape, UpdateShapeCommand updateShapeCommand) {
        Square square = (Square) shape;
        validParameters(updateShapeCommand.getParameters());
        square.setWidth(updateShapeCommand.getParameters().get(0));
        return square;
    }

    public void validParameters(List<Double> parameters) {
        if (parameters.size() != 1) {
            throw new BadNumberOfParametersExceptions(getType(), 1);
        }
    }
}
