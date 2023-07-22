package pl.kurs.geometricshapes.repository.repositoryimpl;

import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.repository.repositorycustom.RectangleRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RectangleRepositoryImpl implements RectangleRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    public RectangleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Rectangle> findAllShapesByFilteredParameters(String type,  String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Rectangle> cq = cb.createQuery(Rectangle.class);

        Root<Rectangle> rectangle = cq.from(Rectangle.class);
        List<Predicate> predicates = new ArrayList<>();

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

        return entityManager.createQuery(cq).getResultList();
    }
}