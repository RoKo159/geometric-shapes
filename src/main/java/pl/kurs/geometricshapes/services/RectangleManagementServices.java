package pl.kurs.geometricshapes.services;

import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.repository.RectangleRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RectangleManagementServices extends ShapeManagementServices<Rectangle, RectangleRepository> {

    public RectangleManagementServices(RectangleRepository repository) {
        super(repository);
    }

    public List<Rectangle> findAllByAreaBetween(double areaFrom, double areaTo) {
        return repository.findAllByAreaBetween(areaFrom, areaTo);
    }

    public List<Rectangle> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return repository.findAllByPerimeterBetween(perimeterFrom, perimeterTo);
    }

    public List<Rectangle> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo) {
        return repository.findAllByCreatedAtBetween(dateFrom, dateTo);
    }

    public List<Rectangle> findAllByCreatedBy(String createdBy) {
        return repository.findAllByCreatedBy(createdBy);
    }

    public List<Rectangle> findAllByWidthBetweenAndAndLengthBetween(double widthFrom, double widthTo, double lengthFrom, double lengthTo) {
        return repository.findAllByWidthBetweenAndAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo);
    }
}
