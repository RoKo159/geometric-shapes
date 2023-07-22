package pl.kurs.geometricshapes.services;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.geometricshapes.exceptions.WrongEntityException;
import pl.kurs.geometricshapes.models.Shapes;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class GenericManagementServices<T extends Identificationable, R extends JpaRepository<T, Long>> implements IManagementService<T> {

    protected R repository;

    public GenericManagementServices(R repository) {
        this.repository = repository;
    }


    @Override
    public T add(T entity) {
        return repository.save(
                Optional.ofNullable(entity)
                        .filter(x -> Objects.isNull(x.getId()))
                        .orElseThrow(() -> new WrongEntityException("Bad Entity"))
        );
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(
                Optional.ofNullable(id)
                        .filter(x -> x > 0)
                        .orElseThrow(() -> new WrongEntityException("Bad id"))
        );
    }

    @Override
    public T edit(T entity) {
        return repository.save(
                Optional.ofNullable(entity)
                        .filter(x -> Objects.nonNull(x.getId()))
                        .orElseThrow(() -> new WrongEntityException("Bad Entity"))
        );
    }

    @Override
    public T get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    public abstract Collection<? extends Shapes> findAllShapesByFilteredParameters(String type, String createdBy, LocalDate dateFrom, LocalDate dateTo, Double areaFrom, Double areaTo, Double perimeterFrom, Double perimeterTo, Double widthFrom, Double widthTo, Double lengthFrom, Double lengthTo, Double radiusFrom, Double radiusTo);
}
