package pl.kurs.geometricshapes.strategy;

import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.models.Shapes;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

public interface ShapeStrategy {

    Shapes createShape(CreateShapeCommand shapeCommand);

    Shapes updateShape(UpdateShapeCommand updateShapeCommand);

    Shapes findById(Long id);

    Type getShapeClass();

    Class<? extends ShapesDto> getShapeDtoClass();

    List<Shapes> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);

    List<Shapes> findAllByAreaBetween(double areaFrom, double areaTo);

    List<Shapes> getAll();

    List<Shapes> findAllByCreatedAtBetween(LocalDate createdFrom, LocalDate createdTo);

    List<Shapes> findAllByCreatedBy(String createdby);

    String getShapeType();


}
