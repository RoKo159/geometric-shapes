package pl.kurs.geometricshapes.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.geometricshapes.commands.CreateShapeCommand;
import pl.kurs.geometricshapes.commands.UpdateShapeCommand;
import pl.kurs.geometricshapes.dto.ShapeDto;
import pl.kurs.geometricshapes.dto.StatusDto;
import pl.kurs.geometricshapes.exceptions.NoShapeTypeException;
import pl.kurs.geometricshapes.exceptions.ObjectNotFoundException;
import pl.kurs.geometricshapes.models.Shape;
import pl.kurs.geometricshapes.repository.ShapeRepository;
import pl.kurs.geometricshapes.filter.ShapeSpecification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShapeService {
    private final ShapeRepository shapeRepository;
    private final ShapeFactoryService shapeFactoryService;
    private final ShapeSpecification shapeSpecification;

    public ShapeService(ShapeRepository shapeRepository, ShapeFactoryService shapeFactoryService, ShapeSpecification shapeSpecification) {
        this.shapeRepository = shapeRepository;
        this.shapeFactoryService = shapeFactoryService;
        this.shapeSpecification = shapeSpecification;
    }

    @Transactional
    public ShapeDto create(CreateShapeCommand createShapeCommand) {
        Shape shape = shapeFactoryService.create(createShapeCommand);
        return shapeFactoryService.createShapeDto(shapeRepository.save(shape));
    }

    @Transactional(readOnly = true)
    public List<ShapeDto> getShapes(Map<String, String> queryParams) {
        List<ShapeDto> results = shapeRepository.findAll(shapeSpecification.getByFilter(queryParams))
                .stream()
                .map(shapeFactoryService::createShapeDto)
                .collect(Collectors.toList());
        return results;
    }

    @Transactional
    public Shape update(UpdateShapeCommand updateShapeCommand) {
        Shape existingShape = shapeRepository.findById(updateShapeCommand.getId())
                .orElseThrow(() -> new NoShapeTypeException("No shape found with id: " + updateShapeCommand.getId()));
        return shapeFactoryService.update(existingShape, updateShapeCommand);
    }

    @Transactional
    public StatusDto delete(Long id) {
        Shape shapeToDelete = shapeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Shape"));
        shapeRepository.delete(shapeToDelete);
        return new StatusDto("Deleted shape with id: " + id);
    }
}
