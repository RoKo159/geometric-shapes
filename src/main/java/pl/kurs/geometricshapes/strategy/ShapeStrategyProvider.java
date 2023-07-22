package pl.kurs.geometricshapes.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShapeStrategyProvider {

    private Map<String, ShapeStrategy> shapeStrategies;

    @Autowired
    public ShapeStrategyProvider(List<ShapeStrategy> shapeStrategies) {
        this.shapeStrategies = shapeStrategies.stream()
                .collect(Collectors.toMap(s -> s.getShapeType().toLowerCase(Locale.ROOT), Function.identity()));
    }

    public ShapeStrategy getStrategy(String shapeType) {
        ShapeStrategy strategy = shapeStrategies.get(shapeType.toLowerCase(Locale.ROOT));
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid shape type: " + shapeType);
        }
        return strategy;
    }

}
