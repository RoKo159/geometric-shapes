package pl.kurs.geometricshapes.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.kurs.geometricshapes.models.ShapeType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateRectangleCommand.class, name = "RECTANGLE"),
        @JsonSubTypes.Type(value = CreateSquareCommand.class, name = "SQUARE"),
        @JsonSubTypes.Type(value = CreateCircleCommand.class, name = "CIRCLE")
})

public abstract class CreateShapeCommand {

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
