package pl.kurs.geometricshapes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricshapes.models.Square;

import java.time.LocalDate;
import java.util.List;

public interface SquareRepository extends JpaRepository<Square, Long> {

    List<Square> findAllByAreaBetween(double areaFrom, double areaTo);

    List<Square> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);

    List<Square> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);

    List<Square> findAllByCreatedBy(String createdBy);

    List<Square> findAllByWidthBetween(double widthFrom, double widthTo);



}
