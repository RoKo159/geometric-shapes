package pl.kurs.geometricshapes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.ShapeType;
import pl.kurs.geometricshapes.repository.RectangleRepository;
import pl.kurs.geometricshapes.models.Rectangle;

@Service
public class RectangleManagementServiceFactory implements ManagementServiceFactory<Rectangle, RectangleRepository> {
    @Autowired
    private RectangleRepository rectangleRepository;

    @Override
    public RectangleManagementServices createManagementService() {
        return new RectangleManagementServices(rectangleRepository);
    }

    @Override
    public RectangleRepository createRepository() {
        return rectangleRepository;
    }

    @Override
    public ShapeType getShapeType() {
        return ShapeType.RECTANGLE;
    }
}