package pl.kurs.geometricshapes.repository.repositorycustom;

import pl.kurs.geometricshapes.models.Rectangle;

import java.time.LocalDate;
import java.util.List;

public interface RectangleRepositoryCustom {

    List<Rectangle> findAllShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo);

}
