package pl.kurs.geometricshapes.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.exceptions.WrongEntityException;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.repository.ShapesRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShapesService<T extends Identificationable> extends GenericManagementServices<T, ShapesRepository<T>> {

    public ShapesService(ShapesRepository<T> repository) {
        super(repository);
    }

    @Override
    public List<T> findAllByAreaBetween(double areaFrom, double areaTo) {
        return repository.findAllByAreaBetween(areaFrom, areaTo);
    }

    @Override
    public List<T> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo) {
        return repository.findAllByPerimeterBetween(perimeterFrom, perimeterTo);
    }

    @Override
    public List<T> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo) {
        return repository.findAllByCreatedAtBetween(dateFrom, dateTo);
    }

    @Override
    public List<T> findAllByCreatedBy(String createdBy) {
        return repository.findAllByCreatedBy(createdBy);
    }
}