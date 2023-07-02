package pl.kurs.geometricshapes.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kurs.geometricshapes.commands.CreateCircleCommand;
import pl.kurs.geometricshapes.commands.CreateRectangleCommand;
import pl.kurs.geometricshapes.commands.CreateSquareCommand;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.models.Square;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<double[], Double> converter = ctx -> ctx.getSource() == null ? null : ctx.getSource()[0];
        Converter<double[],Double> converterForSecondParameter = ctx -> ctx.getSource() == null ? null : ctx.getSource()[1];

        modelMapper.createTypeMap(CreateCircleCommand.class, Circle.class).addMappings(mapper ->
                mapper.using(converter).map(CreateCircleCommand::getParameters, Circle::setRadius));

        modelMapper.createTypeMap(CreateRectangleCommand.class, Rectangle.class).addMappings(mapper -> {
            mapper.using(converter).map(CreateRectangleCommand::getParameters, Rectangle::setLength);
            mapper.using(converterForSecondParameter).map(CreateRectangleCommand::getParameters, Rectangle::setWidth);
        });

        modelMapper.createTypeMap(CreateSquareCommand.class, Square.class).addMappings(mapper ->
                mapper.using(converter).map(CreateSquareCommand::getParameters, Square::setWidth));

        return modelMapper;
    }
}
