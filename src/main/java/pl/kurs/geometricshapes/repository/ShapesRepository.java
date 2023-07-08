package pl.kurs.geometricshapes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.services.Identificationable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShapesRepository<T extends Identificationable> extends JpaRepository<T, Long> {

    List<T> findAllByAreaBetween(double areaFrom, double areaTo);

    List<T> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);

    List<T> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);

    List<T> findAllByCreatedBy(String createdBy);
}
