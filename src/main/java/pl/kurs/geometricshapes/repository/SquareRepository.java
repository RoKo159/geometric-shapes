package pl.kurs.geometricshapes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import pl.kurs.geometricshapes.models.Square;
import pl.kurs.geometricshapes.repository.repositorycustom.SquareRepositoryCustom;

@Repository
public interface SquareRepository extends JpaRepository<Square, Long>, SquareRepositoryCustom {


}