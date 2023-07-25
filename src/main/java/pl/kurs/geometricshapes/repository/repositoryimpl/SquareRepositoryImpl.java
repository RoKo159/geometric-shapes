package pl.kurs.geometricshapes.repository.repositoryimpl;

import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.repository.repositorycustom.SquareRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SquareRepositoryImpl implements SquareRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    public SquareRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Square> findAllShapesByFilteredParameters(String type,  String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Square> cq = cb.createQuery(Square.class);

        Root<Square> square = cq.from(Square.class);
        List<Predicate> predicates = new ArrayList<>();

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

        return entityManager.createQuery(cq).getResultList();
    }
}
