package pl.kurs.geometricshapes.services;

import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.repository.CircleRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CircleManagementServices extends ShapeManagementServices<Circle, CircleRepository> {

    public CircleManagementServices(CircleRepository repository) {
        super(repository);
    }


    public List<Circle> findAllByAreaBetween(double areaFrom, double areaTo) {
        return repository.findAllByAreaBetween(areaFrom, areaTo);
    }

    public List<Circle> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return repository.findAllByPerimeterBetween(perimeterFrom, perimeterTo);
    }

    public List<Circle> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo) {
        return repository.findAllByCreatedAtBetween(dateFrom, dateTo);
    }

    public List<Circle> findAllByCreatedBy(String createdBy) {
        return repository.findAllByCreatedBy(createdBy);
    }

    public List<Circle> findAllByRadiusBetween(double radiusFrom, double radiusTo) {
        return repository.findAllByRadiusBetween(radiusFrom, radiusTo);
    }
}
