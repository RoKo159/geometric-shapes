package pl.kurs.geometricshapes.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.models.Shape;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
public class RectangleFilter implements Filter {
    private static final String WIDTH = "width";
    private static final String WIDTH_FROM = "widthFrom";
    private static final String WIDTH_TO = "widthTo";
    private static final String LENGTH = "length";
    private static final String LENGTH_FROM = "lengthFrom";
    private static final String LENGTH_TO = "lengthTo";

    @Override
    public Expression<Double> getAreaFilter(Root<Shape> root, CriteriaBuilder cb) {
        Root<Rectangle> rectangleRoot = getRectangleRoot(root, cb);
        return cb.prod(rectangleRoot.get(WIDTH), rectangleRoot.get(LENGTH)).as(Double.class);
    }

    @Override
    public Expression<Double> getPerimeterFilter(Root<Shape> root, CriteriaBuilder cb) {
        Root<Rectangle> rectangleRoot = getRectangleRoot(root, cb);
        return cb.sum(cb.prod(2, rectangleRoot.get(WIDTH)), cb.prod(2, rectangleRoot.get(LENGTH))).as(Double.class);
    }

    @Override
    public Specification<Shape> applyCustomFilter(Map<String, String> map) {
        return Specification.where(nonNull(map.getOrDefault(WIDTH_FROM, null)) ? getAttrGreaterOrEq(WIDTH, map.get(WIDTH_FROM)) : null)
                .and(nonNull(map.getOrDefault(WIDTH_TO, null)) ? getAttrLessOrEq(WIDTH, map.get(WIDTH_TO)) : null)
                .and(nonNull(map.getOrDefault(LENGTH_FROM, null)) ? getAttrGreaterOrEq(LENGTH, map.get(LENGTH_FROM)) : null)
                .and(nonNull(map.getOrDefault(LENGTH_TO, null)) ? getAttrLessOrEq(LENGTH, map.get(LENGTH_TO)) : null);
    }

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    public Specification<Shape> getAttrGreaterOrEq(String paramName, String valueFrom) {
        return (root, query, cb) -> cb.ge(getRectangleRoot(root, cb).get(paramName), new BigDecimal(valueFrom));
    }

    public Specification<Shape> getAttrLessOrEq(String paramName, String valueTo) {
        return (root, query, cb) -> cb.le(getRectangleRoot(root, cb).get(paramName), new BigDecimal(valueTo));
    }

    private Root<Rectangle> getRectangleRoot(Root<Shape> root, CriteriaBuilder cb) {
        return cb.treat(root, Rectangle.class);
    }
}
