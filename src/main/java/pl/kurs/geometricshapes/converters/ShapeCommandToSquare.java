package pl.kurs.geometricshapes.converters;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.models.Square;

@Service
public class ShapeCommandToSquare implements Converter<CreateShapeCommand, Square> {
    @Override
    public Square convert(MappingContext<CreateShapeCommand, Square> mappingContext) {
        CreateShapeCommand createShapeCommand = mappingContext.getSource();
        Square square = new Square();
        square.setType(createShapeCommand.getType());
        square.setWidth(createShapeCommand.getParameters().get(0));
        return square;
    }
}
