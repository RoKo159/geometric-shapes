package pl.kurs.geometricshapes.repository.repositoryimpl;

import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.repository.repositorycustom.CircleRepositoryCustom;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CircleRepositoryImpl implements CircleRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    public CircleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Circle> findAllShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double radiusFrom, Double radiusTo) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Circle> cq = cb.createQuery(Circle.class);

        Root<Circle> circle = cq.from(Circle.class);
        List<Predicate> predicates = new ArrayList<>();

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

        return entityManager.createQuery(cq).getResultList();
    }
}