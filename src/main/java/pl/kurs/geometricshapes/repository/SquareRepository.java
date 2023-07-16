package pl.kurs.geometricshapes.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import pl.kurs.geometricshapes.models.QSquare;
import pl.kurs.geometricshapes.models.Square;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

public interface SquareRepository extends JpaRepository<Square, Long>, JpaSpecificationExecutor<Square>, QuerydslPredicateExecutor<Square>, QuerydslBinderCustomizer<QSquare> {

    @Override
    default void customize(QuerydslBindings bindings, QSquare square) {
        bindings.excluding(square.id);

        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

        bindings.bind(square.area).all(((path, value) -> {
            Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        }));

        bindings.bind(square.perimeter).all((path, value) ->
        {Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        });

        bindings.bind(square.createdAt).all((path, value) ->{
            Iterator<? extends LocalDate> iterator = value.iterator();
            LocalDate from = iterator.next();
            if (value.size() >= 2) {
                LocalDate to = iterator.next();
                return Optional.of(path.between(from,to));
            } else {
                return Optional.of(path.goe(from));
            }
        });

        bindings.bind(square.width).all((path, value) ->
        {Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        });
    }


//    List<Square> findAllByAreaBetween(double areaFrom, double areaTo);
//
//    List<Square> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);
//
//    List<Square> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);
//
//    List<Square> findAllByCreatedBy(String createdBy);
//
//    List<Square> findAllByWidthBetween(double widthFrom, double widthTo);
}