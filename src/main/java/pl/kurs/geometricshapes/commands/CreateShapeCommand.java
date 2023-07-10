package pl.kurs.geometricshapes.commands;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Locale;

public class CreateShapeCommand {

    @NotBlank
    private String type;
    @Positive
    @NotBlank
    private double[] parameters;


    public void setType(String type) {
        this.type = type.toUpperCase(Locale.ROOT);
    }


    public String getType() {
        return this.type;
    }

    public double[] getParameters() {
        return parameters;
    }

    public void setParameters(double[] parameters) {
        this.parameters = parameters;
    }
}
