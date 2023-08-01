package pl.kurs.geometricshapes.converters;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.models.Rectangle;

@Service
public class ShapeCommandToRectangle implements Converter<CreateShapeCommand, Rectangle> {
    @Override
    public Rectangle convert(MappingContext<CreateShapeCommand, Rectangle> mappingContext) {
        CreateShapeCommand createShapeCommand = mappingContext.getSource();
        Rectangle rectangle = new Rectangle();
        rectangle.setType(createShapeCommand.getType());
        rectangle.setWidth(createShapeCommand.getParameters().get(0));
        rectangle.setLength(createShapeCommand.getParameters().get(1));
        return rectangle;
    }
}
