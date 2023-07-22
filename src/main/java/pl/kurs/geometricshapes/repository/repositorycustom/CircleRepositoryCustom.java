package pl.kurs.geometricshapes.repository.repositorycustom;

import pl.kurs.geometricshapes.models.Circle;

import java.time.LocalDate;
import java.util.List;

public interface CircleRepositoryCustom {

    List<Circle> findAllShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double radiusFrom, Double radiusTo);
}
