package pl.kurs.geometricshapes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.ShapeType;
import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.repository.SquareRepository;

@Service
public class SquareManagementServiceFactory implements ManagementServiceFactory<Square, SquareRepository> {

    @Autowired
    private SquareRepository squareRepository;

    @Override
    public GenericManagementServices<Square, SquareRepository> createManagementService() {
        return new SquareManagementServices(squareRepository);
    }

    @Override
    public JpaRepository<Square, Long> createRepository() {
        return squareRepository;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.SQUARE;
    }
}
