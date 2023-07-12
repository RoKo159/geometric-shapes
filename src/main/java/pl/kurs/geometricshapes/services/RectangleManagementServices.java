package pl.kurs.geometricshapes.services;

import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.repository.RectangleRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RectangleManagementServices extends GenericManagementServices<Rectangle, RectangleRepository> {

    public RectangleManagementServices(RectangleRepository repository) {
        super(repository);
    }

    @Override
    public List<Rectangle> findAllByAreaBetween(double areaFrom, double areaTo) {
        return repository.findAllByAreaBetween(areaFrom, areaTo);
    }

    @Override
    public List<Rectangle> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return repository.findAllByPerimeterBetween(perimeterFrom, perimeterTo);
    }

    @Override
    public List<Rectangle> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo) {
        return repository.findAllByCreatedAtBetween(dateFrom, dateTo);
    }

    @Override
    public List<Rectangle> findAllByCreatedBy(String createdBy) {
        return repository.findAllByCreatedBy(createdBy);
    }

    public List<Rectangle> findAllByWidthBetweenAndLengthBetween(double widthFrom, double widthTo, double lengthFrom, double lengthTo) {
        return repository.findAllByWidthBetweenAndLengthBetween(widthFrom, widthTo, lengthFrom, lengthTo);
    }
}