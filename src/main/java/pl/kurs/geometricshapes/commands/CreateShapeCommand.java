package pl.kurs.geometricshapes.commands;

import pl.kurs.geometricshapes.validators.SupportedShapeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


public class CreateShapeCommand {

    @NotBlank
    @SupportedShapeType
    private String type;
    @NotEmpty
    private double[] parameters;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getParameters() {
        return parameters;
    }

    public void setParameters(double[] parameters) {
        this.parameters = parameters;
    }
}
