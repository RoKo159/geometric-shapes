package pl.kurs.geometricshapes.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import pl.kurs.geometricshapes.models.Circle;
import pl.kurs.geometricshapes.models.QCircle;


import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

public interface CircleRepository extends JpaRepository<Circle, Long>, QuerydslPredicateExecutor<Circle>, QuerydslBinderCustomizer<QCircle> {


    @Override
    default void customize(QuerydslBindings bindings, QCircle circle) {
        bindings.excluding(circle.id);

        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

        bindings.bind(circle.area).all(((path, value) -> {
            Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        }));

        bindings.bind(circle.perimeter).all((path, value) ->
        {
            Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        });

        bindings.bind(circle.createdAt).all((path, value) -> {
            Iterator<? extends LocalDate> iterator = value.iterator();
            LocalDate from = iterator.next();
            if (value.size() >= 2) {
                LocalDate to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        });

        bindings.bind(circle.radius).all((path, value) ->
        {
            Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        });
    }


//    List<Circle> findAllByAreaBetween(double areaFrom, double areaTo);
//
//    List<Circle> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);
//
//    List<Circle> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);
//
//    List<Circle> findAllByCreatedBy(String createdBy);
//
//    List<Circle> findAllByRadiusBetween(double radiusFrom, double radiusTo);
}
