package pl.kurs.geometricshapes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.Shapes;
import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.repository.SquareRepository;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class SquareManagementServices extends GenericManagementServices<Square, SquareRepository> {

    @Autowired
    public SquareManagementServices(SquareRepository repository) {
        super(repository);
    }

    @Override
    public Collection<? extends Shapes> findAllShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo, Double radiusFrom, Double radiusTo) {
        return repository.findAllShapesByFilteredParameters(type, createdBy, dateFrom, dateTo, areaFrom, areaTo, perimeterFrom, perimeterTo, widthFrom, widthTo);
    }

}
