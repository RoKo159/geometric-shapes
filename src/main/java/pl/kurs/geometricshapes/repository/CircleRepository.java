package pl.kurs.geometricshapes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricshapes.models.Circle;

import java.time.LocalDate;
import java.util.List;

public interface CircleRepository extends JpaRepository<Circle, Long> {

    List<Circle> findAllByAreaBetween(double areaFrom, double areaTo);

    List<Circle> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);

    List<Circle> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);

    List<Circle> findAllByCreatedBy(String createdBy);

    List<Circle> findAllByRadiusBetween(double radiusFrom, double radiusTo);
}
