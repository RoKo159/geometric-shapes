package pl.kurs.geometricshapes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.repository.repositorycustom.CircleRepositoryCustom;

@Repository
public interface CircleRepository extends JpaRepository<Circle, Long>, CircleRepositoryCustom {

}
