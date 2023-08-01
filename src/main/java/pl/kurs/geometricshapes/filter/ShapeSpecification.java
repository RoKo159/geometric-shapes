package pl.kurs.geometricshapes.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.kurs.geometricshapes.filter.Filter;
import pl.kurs.geometricshapes.models.Shape;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Component
public class ShapeSpecification {
    private final List<Filter> filters;
    private static final String TYPE = "type";
    private static final String CREATED_BY = "createdBy";
    private static final String CREATED_AT = "createdAt";
    private static final String CREATED_AT_FROM = "createdAtFrom";
    private static final String CREATED_AT_TO = "createdAtTo";
    private static final String AREA_FROM = "areaFrom";
    private static final String AREA_TO = "areaTo";
    private static final String PERIMETER_FROM = "perimeterFrom";
    private static final String PERIMETER_TO = "perimeterTo";

    public ShapeSpecification(List<Filter> filters) {
        this.filters = filters;
    }

    public Specification<Shape> getByFilter(Map<String, String> map) {
        return Specification.where(isNotBlank(map.getOrDefault(TYPE, null)) ? getStringAttrEq(TYPE, map.get(TYPE)) : null)
                .and(isNotBlank(map.getOrDefault(CREATED_BY, null)) ? getStringAttrEq(CREATED_BY, map.get(CREATED_BY)) : null)
                .and(nonNull(map.getOrDefault(CREATED_AT_FROM, null)) ? getDateFromAttr(CREATED_AT, map.get(CREATED_AT_FROM)) : null)
                .and(nonNull(map.getOrDefault(CREATED_AT_TO, null)) ? getDateToAttr(CREATED_AT, map.get(CREATED_AT_TO)) : null)
                .and(nonNull(map.getOrDefault(AREA_FROM, null)) ? getAreaFrom(map.get(AREA_FROM)) : null)
                .and(nonNull(map.getOrDefault(AREA_TO, null)) ? getAreaTo(map.get(AREA_TO)) : null)
                .and(nonNull(map.getOrDefault(PERIMETER_FROM, null)) ? getPerimeterFrom(map.get(PERIMETER_FROM)) : null)
                .and(nonNull(map.getOrDefault(PERIMETER_TO, null)) ? getPerimeterTo(map.get(PERIMETER_TO)) : null)
                .and(getCustomFilter(map));
    }

    public Specification<Shape> getStringAttrEq(String paramName, String value) {
        return (root, query, cb) ->
                cb.equal(root.get(paramName), value);
    }


    public Specification<Shape> getDateFromAttr(String paramName, String createdFromValue) {
        LocalDate createdFrom = LocalDate.parse(createdFromValue);
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(paramName), createdFrom);
    }

    public Specification<Shape> getDateToAttr(String paramName, String createdToValue) {
        LocalDate createdTo = LocalDate.parse(createdToValue);
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(paramName), createdTo);
    }

    public Specification<Shape> getAreaFrom(String areaFromValue) {
        BigDecimal areaFrom = new BigDecimal(areaFromValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getAreaFrom(TYPE, areaFrom)).collect(Collectors.toList());
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getAreaTo(String areaToValue) {
        BigDecimal areaTo = new BigDecimal(areaToValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getAreaTo(TYPE, areaTo)).collect(Collectors.toList());
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getPerimeterFrom(String perimeterFromValue) {
        BigDecimal perimeterFrom = new BigDecimal(perimeterFromValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getPerimeterFrom(TYPE, perimeterFrom)).collect(Collectors.toList());
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getPerimeterTo(String perimeterToValue) {
        BigDecimal perimeterTo = new BigDecimal(perimeterToValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getPerimeterTo(TYPE, perimeterTo)).collect(Collectors.toList());
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getCustomFilter(Map<String, String> map) {
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.applyCustomFilter(map)).collect(Collectors.toList());
        return getSpecAndFromList(specifications);
    }

    private Specification<Shape> getSpecOrFromList(List<Specification<Shape>> specifications) {
        Specification<Shape> spec = Specification.where(null);
        for (Specification<Shape> s : specifications) {
            spec = spec.or(s);
        }
        return spec;
    }

    private Specification<Shape> getSpecAndFromList(List<Specification<Shape>> specifications) {
        Specification<Shape> spec = Specification.where(null);
        for (Specification<Shape> s : specifications) {
            spec = spec.and(s);
        }
        return spec;
    }


}
