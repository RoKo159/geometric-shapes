package pl.kurs.geometricshapes.commands;

import pl.kurs.geometricshapes.models.ShapeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CreateShapeCommand {

    @NotBlank
    private ShapeType type;
    @Positive
    @NotBlank
    private double[] parameters;


    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType type) {
        this.type = type;
    }

    public double[] getParameters() {
        return parameters;
    }

    public void setParameters(double[] parameters) {
        this.parameters = parameters;
    }
}
