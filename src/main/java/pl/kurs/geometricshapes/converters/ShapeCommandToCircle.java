package pl.kurs.geometricshapes.converters;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.models.Circle;

@Service
public class ShapeCommandToCircle implements Converter<CreateShapeCommand, Circle> {
    @Override
    public Circle convert(MappingContext<CreateShapeCommand, Circle> mappingContext) {
        CreateShapeCommand source = mappingContext.getSource();
        Circle circle = new Circle();
        circle.setType(source.getType());
        circle.setRadius(source.getParameters().get(0));
        return circle;
    }

}
