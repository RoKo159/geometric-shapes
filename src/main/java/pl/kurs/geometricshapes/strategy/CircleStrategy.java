package pl.kurs.geometricshapes.strategy;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.CircleDto;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.services.CircleManagementServices;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CircleStrategy implements ShapeStrategy {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CircleManagementServices circleManagementService;

    @Override
    public Circle createShape(CreateShapeCommand shapeCommand) {
        Circle circleForSave = modelMapper.map(shapeCommand, getShapeClass());
        circleManagementService.add(circleForSave);
        return circleForSave;
    }

    @Override
    public Shapes updateShape(UpdateShapeCommand updateShapeCommand) {
        Circle circleForUpdate = circleManagementService.get(updateShapeCommand.getId());
        modelMapper.map(updateShapeCommand, circleForUpdate);
        circleManagementService.edit(circleForUpdate);
        return circleForUpdate;
    }
    @Override
    public Shapes findById(Long id) {
        return circleManagementService.get(id);
    }


    @Override
    public Type getShapeClass() {
        return Circle.class;
    }

    @Override
    public Class<? extends ShapesDto> getShapeDtoClass() {
        return CircleDto.class;
    }

    @Override
    public List<Shapes> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return new ArrayList<>(circleManagementService.findAllByPerimeterBetween(perimeterFrom, perimeterTo));
    }

    @Override
    public List<Shapes> findAllByAreaBetween(double areaFrom, double areaTo) {
        return new ArrayList<>(circleManagementService.findAllByAreaBetween(areaFrom, areaTo));
    }

    @Override
    public List<Shapes> getAll() {
        return new ArrayList<>(circleManagementService.getAll());
    }

    @Override
    public List<Shapes> findAllByCreatedAtBetween(LocalDate createdFrom, LocalDate createdTo) {
        return new ArrayList<>(circleManagementService.findAllByCreatedAtBetween(createdFrom, createdTo));
    }

    @Override
    public List<Shapes> findAllByCreatedBy(String createdby) {
        return new ArrayList<>(circleManagementService.findAllByCreatedBy(createdby));
    }

    @Override
    public String getShapeType() {
        return "circle";
    }

    public List<Circle> findAllByRadiusBetween(double radiusFrom, double radiusTo) {
        return circleManagementService.findAllByRadiusBetween(radiusFrom, radiusTo);
    }
}