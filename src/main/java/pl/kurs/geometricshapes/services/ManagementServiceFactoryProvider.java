package pl.kurs.geometricshapes.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.kurs.geometricshapes.models.ShapeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ManagementServiceFactoryProvider {
    private final Map<ShapeType, ManagementServiceFactory<?, ?>> factories;

    public ManagementServiceFactoryProvider(List<ManagementServiceFactory<?, ?>> factoriesList) {
        this.factories = factoriesList.stream().collect(
                Collectors.toMap(
                        factory -> factory.getShapeType(),
                        factory -> factory
                )
        );
    }

    public <T extends Identificationable, R extends JpaRepository<T, Long>> ManagementServiceFactory<T, R> getFactory(ShapeType type) {
        return (ManagementServiceFactory<T, R>) factories.get(type);
    }

    public List<ManagementServiceFactory<?, ?>> getAllFactories() {
        return new ArrayList<>(factories.values());
    }
}

