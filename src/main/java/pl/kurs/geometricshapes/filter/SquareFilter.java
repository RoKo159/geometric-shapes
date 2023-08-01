package pl.kurs.geometricshapes.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.kurs.geometricshapes.models.Shape;
import pl.kurs.geometricshapes.models.Square;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
public class SquareFilter implements Filter {
    private static final String WIDTH = "width";
    private static final String WIDTH_FROM = "widthFrom";
    private static final String WIDTH_TO = "widthTo";

    @Override
    public Expression<Double> getAreaFilter(Root<Shape> root, CriteriaBuilder cb) {
        Root<Square> squareRoot = getSquareRoot(root, cb);
        return cb.prod(squareRoot.get(WIDTH), squareRoot.get(WIDTH)).as(Double.class);
    }

    @Override
    public Expression<Double> getPerimeterFilter(Root<Shape> root, CriteriaBuilder cb) {
        return cb.prod(4, getSquareRoot(root, cb).get(WIDTH)).as(Double.class);
    }

    @Override
    public Specification<Shape> applyCustomFilter(Map<String, String> map) {
        return Specification.where(nonNull(map.getOrDefault(WIDTH_FROM, null)) ? getAttrGreaterOrEq(map.get(WIDTH_FROM)) : null)
                .and(nonNull(map.getOrDefault(WIDTH_TO, null)) ? getAttrLessOrEq(map.get(WIDTH_TO)) : null);
    }

    @Override
    public String getType() {
        return "SQUARE";
    }

    private Specification<Shape> getAttrGreaterOrEq(String valueFrom) {
        return (root, query, cb) -> cb.ge(getSquareRoot(root, cb).get(WIDTH), new BigDecimal(valueFrom));
    }

    private Specification<Shape> getAttrLessOrEq(String valueTo) {
        return (root, query, cb) -> cb.le(getSquareRoot(root, cb).get(WIDTH), new BigDecimal(valueTo));
    }

    private Root<Square> getSquareRoot(Root<Shape> root, CriteriaBuilder cb) {
        return cb.treat(root, Square.class);
    }
}
