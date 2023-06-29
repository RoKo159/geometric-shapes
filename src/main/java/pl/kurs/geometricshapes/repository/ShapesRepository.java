package pl.kurs.geometricshapes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricshapes.models.Shapes;

public interface ShapesRepository extends JpaRepository<Shapes, Long> {
    
}
