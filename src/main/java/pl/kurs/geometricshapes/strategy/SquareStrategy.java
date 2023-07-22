package pl.kurs.geometricshapes.strategy;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.services.SquareManagementServices;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SquareStrategy implements ShapeStrategy {

    @PersistenceContext
    private EntityManager entityManager;

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
        Square squareForUpdate = squareManagementService.get(updateShapeCommand.getId());
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
    public List<Shapes> getAll() {
        return new ArrayList<>(squareManagementService.getAll());
    }

//    @Override
//    public List<Shapes> getShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo, Double radiusFrom, Double radiusTo) {
//        return new ArrayList<>(squareManagementService.findAllShapesByFilteredParameters(type, createdBy, dateFrom, dateTo, areaFrom, areaTo, perimeterFrom, perimeterTo, widthFrom, widthTo, lengthFrom, lengthTo, widthFrom, widthTo));
//    }

    @Override
    public List<Shapes> getShapesByFilteredParameters(Map<String, String> allParams) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Square> cq = cb.createQuery(Square.class);

        Root<Square> square = cq.from(Square.class);
        List<Predicate> predicates = new ArrayList<>();

        String type = allParams.get("type");
        String createdBy = allParams.get("createdby");
        LocalDate dateFrom = allParams.get("datefrom") != null ? LocalDate.parse(allParams.get("datefrom")) : null;
        LocalDate dateTo = allParams.get("dateto") != null ? LocalDate.parse(allParams.get("dateto")) : null;
        Double areaFrom = allParams.get("areafrom") != null ? Double.parseDouble(allParams.get("areafrom")) : null;
        Double areaTo = allParams.get("areato") != null ? Double.parseDouble(allParams.get("areato")) : null;
        Double perimeterFrom = allParams.get("perimeterfrom") != null ? Double.parseDouble(allParams.get("perimeterfrom")) : null;
        Double perimeterTo = allParams.get("perimeterto") != null ? Double.parseDouble(allParams.get("perimeterto")) : null;
        Double widthFrom = allParams.get("widthfrom") != null ? Double.parseDouble(allParams.get("widthfrom")) : null;
        Double widthTo = allParams.get("widthto") != null ? Double.parseDouble(allParams.get("widthto")) : null;

        if (type != null) {
            predicates.add(cb.equal(square.get("type"), type));
        }
        if (createdBy != null) {
            predicates.add(cb.equal(square.get("createdBy"), createdBy));
        }
        if (dateFrom != null && dateTo != null) {
            predicates.add(cb.between(square.get("createdAt"), dateFrom, dateTo));
        }
        if (areaFrom != null && areaTo != null) {
            predicates.add(cb.between(square.get("area"), areaFrom, areaTo));
        }
        if (perimeterFrom != null && perimeterTo != null) {
            predicates.add(cb.between(square.get("perimeter"), perimeterFrom, perimeterTo));
        }
        if (widthFrom != null && widthTo != null) {
            predicates.add(cb.between(square.get("width"), widthFrom, widthTo));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return new ArrayList<>(entityManager.createQuery(cq).getResultList());
    }


    @Override
    public String getShapeType() {
        return "square";
    }

}