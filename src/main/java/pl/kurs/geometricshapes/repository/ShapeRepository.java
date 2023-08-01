package pl.kurs.geometricshapes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kurs.geometricshapes.models.Shape;

public interface ShapeRepository extends JpaRepository<Shape, Long>, JpaSpecificationExecutor<Shape> {

}
