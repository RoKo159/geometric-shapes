package pl.kurs.geometricshapes.repository.repositorycustom;

import pl.kurs.geometricshapes.models.Square;

import java.time.LocalDate;
import java.util.List;

public interface SquareRepositoryCustom {

    List<Square> findAllShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo);
}
