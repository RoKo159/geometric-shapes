package pl.kurs.geometricshapes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kurs.geometricshapes.models.Rectangle;
import pl.kurs.geometricshapes.repository.repositorycustom.RectangleRepositoryCustom;

@Repository
public interface RectangleRepository extends JpaRepository<Rectangle, Long>, RectangleRepositoryCustom {

}
