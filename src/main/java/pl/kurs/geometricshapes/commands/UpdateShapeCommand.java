package pl.kurs.geometricshapes.commands;

import pl.kurs.geometricshapes.validators.SupportedShapeType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateShapeCommand {

    @NotNull
    private Long id;
    @SupportedShapeType
    private String type;
    @NotEmpty
    private double[] parameters;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
