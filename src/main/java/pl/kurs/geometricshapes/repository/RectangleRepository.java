package pl.kurs.geometricshapes.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import pl.kurs.geometricshapes.models.QRectangle;
import pl.kurs.geometricshapes.models.Rectangle;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

public interface RectangleRepository extends JpaRepository<Rectangle, Long>, JpaSpecificationExecutor<Rectangle>, QuerydslPredicateExecutor<Rectangle>, QuerydslBinderCustomizer<QRectangle> {

    @Override
    default void customize(QuerydslBindings bindings, QRectangle rectangle) {
        bindings.excluding(rectangle.id);

        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

        bindings.bind(rectangle.area).all(((path, value) -> {
            Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        }));

        bindings.bind(rectangle.perimeter).all((path, value) ->
        {Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        });

        bindings.bind(rectangle.createdAt).all((path, value) ->{
            Iterator<? extends LocalDate> iterator = value.iterator();
            LocalDate from = iterator.next();
            if (value.size() >= 2) {
                LocalDate to = iterator.next();
                return Optional.of(path.between(from,to));
            } else {
                return Optional.of(path.goe(from));
            }
        });

        bindings.bind(rectangle.width).all((path, value) ->
        {Iterator<? extends Double> iterator = value.iterator();
            Double from = iterator.next();
            if (value.size() >= 2) {
                Double to = iterator.next();
                return Optional.of(path.between(from, to));
            } else {
                return Optional.of(path.goe(from));
            }
        });

        bindings.bind(rectangle.length).all((path, value) ->
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

    //    List<Rectangle> findAllByAreaBetween(double areaFrom, double areaTo);
//
//    List<Rectangle> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);
//
//    List<Rectangle> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);
//
//    List<Rectangle> findAllByCreatedBy(String createdBy);
//
//    List<Rectangle> findAllByWidthBetweenAndLengthBetween(double widthFrom, double widthTo, double lengthFrom, double lengthTo);
}
