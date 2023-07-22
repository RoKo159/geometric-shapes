package pl.kurs.geometricshapes.strategy;

import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.models.Shapes;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ShapeStrategy {

    Shapes createShape(CreateShapeCommand shapeCommand);

    Shapes updateShape(UpdateShapeCommand updateShapeCommand);

    Shapes findById(Long id);

    Type getShapeClass();

    Class<? extends ShapesDto> getShapeDtoClass();

    List<Shapes> getAll();

    //List<Shapes> getShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo, Double radiusFrom, Double radiusTo);
    List<Shapes> getShapesByFilteredParameters(Map<String,String> allParams);

    String getShapeType();
}
