package pl.kurs.geometricshapes.strategy;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.services.SquareManagementServices;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SquareStrategy implements ShapeStrategy {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SquareManagementServices squareManagementService;

    @Override
    public Square createShape(CreateShapeCommand shapeCommand) {
        Square squareForSave = modelMapper.map(shapeCommand, getShapeClass());
        squareManagementService.add(squareForSave);
        return squareForSave;
    }

    @Override
    public Shapes updateShape(UpdateShapeCommand updateShapeCommand) {
        Optional<Square> squareForUpdateOptional = Optional.ofNullable(squareManagementService.get(updateShapeCommand.getId()));
        Square squareForUpdate = squareForUpdateOptional.get();
        modelMapper.map(updateShapeCommand, squareForUpdate);
        squareManagementService.edit(squareForUpdate);
        return squareForUpdate;

    }


    @Override
    public Shapes findById(Long id) {
        return null;
    }

    @Override
    public Type getShapeClass() {
        return Square.class;
    }

    @Override
    public Class<? extends ShapesDto> getShapeDtoClass() {
        return SquareDto.class;
    }

    @Override
    public List<Shapes> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return new ArrayList<>(squareManagementService.findAllByPerimeterBetween(perimeterFrom, perimeterTo));
    }

    @Override
    public List<Shapes> findAllByAreaBetween(double areaFrom, double areaTo) {
        return new ArrayList<>(squareManagementService.findAllByAreaBetween(areaFrom, areaTo));
    }

    @Override
    public List<Shapes> getAll() {
        return new ArrayList<>(squareManagementService.getAll());
    }

    @Override
    public List<Shapes> findAllByCreatedAtBetween(LocalDate createdFrom, LocalDate createdTo) {
        return new ArrayList<>(squareManagementService.findAllByCreatedAtBetween(createdFrom, createdTo));
    }

    @Override
    public List<Shapes> findAllByCreatedBy(String createdby) {
        return new ArrayList<>(squareManagementService.findAllByCreatedBy(createdby));
    }

    @Override
    public String getShapeType() {
        return "square";
    }

    public List<Square> findAllByWidthBetween(double widthFrom, double widthTo) {
        return squareManagementService.findAllByWidthBetween(widthFrom, widthTo);
    }
}