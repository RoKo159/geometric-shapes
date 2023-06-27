package pl.kurs.geometricshapes.services;

import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.repository.SquareRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SquareManagementServices extends GenericManagementServices<Square, SquareRepository> {

    public SquareManagementServices(SquareRepository repository) {
        super(repository);
    }

    public List<Square> findAllByAreaBetween(double areaFrom, double areaTo) {
        return repository.findAllByAreaBetween(areaFrom, areaTo);
    }

    public List<Square> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return repository.findAllByPerimeterBetween(perimeterFrom, perimeterTo);
    }

    public List<Square> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo) {
        return repository.findAllByCreatedAtBetween(dateFrom, dateTo);
    }

    public List<Square> findAllByCreatedBy(String createdBy) {
        return repository.findAllByCreatedBy(createdBy);
    }

    public List<Square> findAllByWidthBetween(double widthFrom, double widthTo) {
        return repository.findAllByWidthBetween(widthFrom, widthTo);
    }
}
