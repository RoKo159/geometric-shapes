package pl.kurs.geometricshapes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricshapes.models.Rectangle;

import java.time.LocalDate;

import java.util.List;


public interface RectangleRepository extends JpaRepository<Rectangle, Long> {


    List<Rectangle> findAllByAreaBetween(double areaFrom, double areaTo);

    List<Rectangle> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);

    List<Rectangle> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);

    List<Rectangle> findAllByCreatedBy(String createdBy);

    List<Rectangle> findAllByWidthBetweenAndLengthBetween(double widthFrom, double widthTo, double lengthFrom, double lengthTo);
}
