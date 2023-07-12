package pl.kurs.geometricshapes.strategy;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.RectangleDto;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.services.RectangleManagementServices;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RectangleStrategy implements ShapeStrategy {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RectangleManagementServices rectangleManagementService;

    @Override
    public Rectangle createShape(CreateShapeCommand shapeCommand) {
        Rectangle rectangleForSave = modelMapper.map(shapeCommand, Rectangle.class);
        rectangleManagementService.add(rectangleForSave);
        return rectangleForSave;
    }

    @Override
    public Shapes updateShape(UpdateShapeCommand updateShapeCommand) {
        Optional<Rectangle> rectangleForUpdateOptional = Optional.ofNullable(rectangleManagementService.get(updateShapeCommand.getId()));
        Rectangle rectangleForUpdate = rectangleForUpdateOptional.get();
        modelMapper.map(updateShapeCommand, rectangleForUpdate);
        rectangleManagementService.edit(rectangleForUpdate);
        return rectangleForUpdate;
    }


    @Override
    public Shapes findById(Long id) {
        return rectangleManagementService.get(id);
    }

    @Override
    public Type getShapeClass() {
        return Rectangle.class;
    }

    @Override
    public Class<? extends ShapesDto> getShapeDtoClass() {
        return RectangleDto.class;
    }

    @Override
    public List<Shapes> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return new ArrayList<>(rectangleManagementService.findAllByPerimeterBetween(perimeterFrom, perimeterTo));
    }

    @Override
    public List<Shapes> findAllByAreaBetween(double areaFrom, double areaTo) {
        return new ArrayList<>(rectangleManagementService.findAllByAreaBetween(areaFrom, areaTo));
    }

    @Override
    public List<Shapes> getAll() {
        return new ArrayList<>(rectangleManagementService.getAll());
    }

    @Override
    public List<Shapes> findAllByCreatedAtBetween(LocalDate createdFrom, LocalDate createdTo) {
        return new ArrayList<>(rectangleManagementService.findAllByCreatedAtBetween(createdFrom, createdTo));
    }

    @Override
    public List<Shapes> findAllByCreatedBy(String createdby) {
        return new ArrayList<>(rectangleManagementService.findAllByCreatedBy(createdby));
    }

    @Override
    public String getShapeType() {
        return "rectangle";
    }

    public List<Rectangle> findAllByWidthBetweenAndLengthBetween(double widthFrom, double widthTo, double lengthFrom, double lengthTo) {
        return rectangleManagementService.findAllByWidthBetweenAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo);
    }
}