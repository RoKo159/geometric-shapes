package pl.kurs.geometricshapes.services;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricshapes.models.ShapeType;


public interface ManagementServiceFactory<T extends Identificationable, R extends JpaRepository<T, Long>> {

    GenericManagementServices<T, R> createManagementService();
    JpaRepository<T, Long> createRepository();
    ShapeType getShapeType();

}