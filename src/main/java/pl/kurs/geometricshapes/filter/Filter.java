package pl.kurs.geometricshapes.filter;

import org.springframework.data.jpa.domain.Specification;
import pl.kurs.geometricshapes.models.Shape;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Map;

public interface Filter {

    Expression<Double> getAreaFilter(Root<Shape> root, CriteriaBuilder cb);

    Expression<Double> getPerimeterFilter(Root<Shape> root, CriteriaBuilder cb);

    Specification<Shape> applyCustomFilter(Map<String, String> map);

    String getType();

    default Specification<Shape> getAreaFrom(String typeField, BigDecimal areaFrom) {
        return (root, query, cb) -> cb.ge(getAreaByType(cb, root, typeField), areaFrom);
    }

    default Specification<Shape> getAreaTo(String typeField, BigDecimal areaTo) {
        return (root, query, cb) -> cb.le(getAreaByType(cb, root, typeField), areaTo);
    }

    default Specification<Shape> getPerimeterFrom(String typeField, BigDecimal perimeterFrom) {
        return (root, query, cb) -> cb.ge(getPerimeterByType(cb, root, typeField), perimeterFrom);
    }

    default Specification<Shape> getPerimeterTo(String typeField, BigDecimal perimeterTo) {
        return (root, query, cb) -> cb.le(getPerimeterByType(cb, root, typeField), perimeterTo);
    }

    private Expression<Double> getAreaByType(CriteriaBuilder cb, Root<Shape> root, String typeField) {
        return cb.selectCase()
                .when(cb.equal(root.get(typeField), getType()), getAreaFilter(root, cb))
                .as(Double.class);
    }

    private Expression<Double> getPerimeterByType(CriteriaBuilder cb, Root<Shape> root, String typeField) {
        return cb.selectCase()
                .when(cb.equal(root.get(typeField), getType()), getPerimeterFilter(root, cb))
                .as(Double.class);
    }
}
