package pl.kurs.geometricshapes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.ShapeType;
import pl.kurs.geometricshapes.repository.CircleRepository;
import pl.kurs.geometricshapes.models.Circle;

@Service
public class CircleManagementServiceFactory implements ManagementServiceFactory<Circle, CircleRepository> {
    @Autowired
    private CircleRepository circleRepository;

    @Override
    public CircleManagementServices createManagementService() {
        return new CircleManagementServices(circleRepository);
    }

    @Override
    public CircleRepository createRepository() {
        return circleRepository;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.CIRCLE;
    }
}
