package pl.kurs.geometricshapes.commands;

import pl.kurs.geometricshapes.models.ShapeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EditShapeCommand {

    @NotNull
    private Long id;
    @NotBlank
    private ShapeType type;
    @Positive
    @NotBlank
    private double[] parameters;

    public Long getId() {
        return id;
    }

    public ShapeType getType() {
        return type;
    }

    public double[] getParameters() {
        return parameters;
    }
}
