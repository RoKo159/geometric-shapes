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
public class CircleStrategy implements ShapeStrategy {

    @PersistenceContext
    private EntityManager entityManager;

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
    public List<Shapes> getAll() {
        return new ArrayList<>(circleManagementService.getAll());
    }

//    @Override
//    public List<Shapes> getShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo, Double radiusFrom, Double radiusTo) {
//        return new ArrayList<>(circleManagementService.findAllShapesByFilteredParameters(type, createdBy, dateFrom, dateTo, areaFrom, areaTo, perimeterFrom, perimeterTo, widthFrom, widthTo, lengthFrom, lengthTo, radiusFrom, radiusTo));
//    }

    @Override
    public List<Shapes> getShapesByFilteredParameters(Map<String, String> allParams) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Circle> cq = cb.createQuery(Circle.class);

        Root<Circle> circle = cq.from(Circle.class);
        List<Predicate> predicates = new ArrayList<>();

        String type = allParams.get("type");
        String createdBy = allParams.get("createdby");
        LocalDate dateFrom = allParams.get("datefrom") != null ? LocalDate.parse(allParams.get("datefrom")) : null;
        LocalDate dateTo = allParams.get("dateto") != null ? LocalDate.parse(allParams.get("dateto")) : null;
        Double areaFrom = allParams.get("areafrom") != null ? Double.parseDouble(allParams.get("areafrom")) : null;
        Double areaTo = allParams.get("areato") != null ? Double.parseDouble(allParams.get("areato")) : null;
        Double perimeterFrom = allParams.get("perimeterfrom") != null ? Double.parseDouble(allParams.get("perimeterfrom")) : null;
        Double perimeterTo = allParams.get("perimeterto") != null ? Double.parseDouble(allParams.get("perimeterto")) : null;
        Double radiusFrom = allParams.get("radiusfrom") != null ? Double.parseDouble(allParams.get("radiusfrom")) : null;
        Double radiusTo = allParams.get("radiusto") != null ? Double.parseDouble(allParams.get("radiusto")) : null;

        if (type != null) {
            predicates.add(cb.equal(circle.get("type"), type));
        }
        if (createdBy != null) {
            predicates.add(cb.equal(circle.get("createdBy"), createdBy));
        }
        if (dateFrom != null && dateTo != null) {
            predicates.add(cb.between(circle.get("createdAt"), dateFrom, dateTo));
        }
        if (areaFrom != null && areaTo != null) {
            predicates.add(cb.between(circle.get("area"), areaFrom, areaTo));
        }
        if (perimeterFrom != null && perimeterTo != null) {
            predicates.add(cb.between(circle.get("perimeter"), perimeterFrom, perimeterTo));
        }
        if (radiusFrom != null && radiusTo != null) {
            predicates.add(cb.between(circle.get("radius"), radiusFrom, radiusTo));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return new ArrayList<>(entityManager.createQuery(cq).getResultList());
    }


    @Override
    public String getShapeType() {
        return "circle";
    }

}