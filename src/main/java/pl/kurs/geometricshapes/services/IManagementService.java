package pl.kurs.geometricshapes.services;

import java.time.LocalDate;
import java.util.List;

public interface IManagementService<T> {

    T add(T entity);

    void delete(Long id);

    T edit(T entity);

    T get(Long id);

    List<T> getAll();

    List<T> findAllByAreaBetween(double areaFrom, double areaTo);

    List<T> findAllByPerimeterBetween(double perimeterFrom, double perimeterTo);

    List<T> findAllByCreatedAtBetween(LocalDate dateFrom, LocalDate dateTo);

    List<T> findAllByCreatedBy(String createdBy);

}
