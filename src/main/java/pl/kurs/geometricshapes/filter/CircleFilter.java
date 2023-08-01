package pl.kurs.geometricshapes.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.models.Shape;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
public class CircleFilter implements Filter {
    private static final String RADIUS = "radius";
    private static final String RADIUS_FROM = "radiusFrom";
    private static final String RADIUS_TO = "radiusTo";

    @Override
    public Expression<Double> getAreaFilter(Root<Shape> root, CriteriaBuilder cb) {
        Root<Circle> circleRoot = getCircleRoot(root, cb);
        return cb.prod(cb.prod(circleRoot.get(RADIUS), circleRoot.get(RADIUS)), Math.PI).as(Double.class);
    }

    @Override
    public Expression<Double> getPerimeterFilter(Root<Shape> root, CriteriaBuilder cb) {
        return cb.prod(cb.prod(2, getCircleRoot(root, cb).get(RADIUS)), Math.PI).as(Double.class);
    }

    @Override
    public Specification<Shape> applyCustomFilter(Map<String, String> map) {
        return Specification.where(nonNull(map.getOrDefault(RADIUS_FROM, null)) ? getAttrGreaterOrEq(RADIUS, map.get(RADIUS_FROM)) : null)
                .and(nonNull(map.getOrDefault(RADIUS_TO, null)) ? getAttrLessOrEq(RADIUS, map.get(RADIUS_TO)) : null);
    }

    @Override
    public String getType() {
        return "CIRCLE";
    }

    public Specification<Shape> getAttrGreaterOrEq(String paramName, String valueFrom) {
        return (root, query, cb) -> cb.ge(getCircleRoot(root, cb).get(paramName), new BigDecimal(valueFrom));
    }

    public Specification<Shape> getAttrLessOrEq(String paramName, String valueTo) {
        return (root, query, cb) -> cb.le(getCircleRoot(root, cb).get(paramName), new BigDecimal(valueTo));
    }

    private Root<Circle> getCircleRoot(Root<Shape> root, CriteriaBuilder cb) {
        return cb.treat(root, Circle.class);
    }
}
