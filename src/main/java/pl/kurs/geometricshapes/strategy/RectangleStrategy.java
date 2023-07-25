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
public class RectangleStrategy implements ShapeStrategy {

    @PersistenceContext
    private EntityManager entityManager;

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
        Rectangle rectangleForUpdate = rectangleManagementService.get(updateShapeCommand.getId());
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
    public List<Shapes> getAll() {
        return new ArrayList<>(rectangleManagementService.getAll());
    }

    @Override
    public List<Shapes> getShapesByFilteredParameters(Map<String, String> allParams) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rectangle> cq = cb.createQuery(Rectangle.class);

        Root<Rectangle> rectangle = cq.from(Rectangle.class);
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
        Double lengthFrom = allParams.get("lengthFrom") != null ? Double.parseDouble(allParams.get("lengthFrom")) : null;
        Double lengthTo = allParams.get("lengthto") != null ? Double.parseDouble(allParams.get("lengthto")) : null;

        if (type != null) {
            predicates.add(cb.equal(rectangle.get("type"), type));
        }
        if (createdBy != null) {
            predicates.add(cb.equal(rectangle.get("createdBy"), createdBy));
        }
        if (dateFrom != null && dateTo != null) {
            predicates.add(cb.between(rectangle.get("createdAt"), dateFrom, dateTo));
        }
        if (areaFrom != null && areaTo != null) {
            predicates.add(cb.between(rectangle.get("area"), areaFrom, areaTo));
        }
        if (perimeterFrom != null && perimeterTo != null) {
            predicates.add(cb.between(rectangle.get("perimeter"), perimeterFrom, perimeterTo));
        }
        if (widthFrom != null && widthTo != null) {
            predicates.add(cb.between(rectangle.get("width"), widthFrom, widthTo));
        }
        if (lengthFrom != null && lengthTo != null) {
            predicates.add(cb.between(rectangle.get("length"), lengthFrom, lengthTo));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return new ArrayList<>(entityManager.createQuery(cq).getResultList());
    }

    @Override
    public String getShapeType() {
        return "rectangle";
    }

}