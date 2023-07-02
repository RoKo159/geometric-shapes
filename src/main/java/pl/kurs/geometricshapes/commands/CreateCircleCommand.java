package pl.kurs.geometricshapes.commands;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CreateCircleCommand extends CreateShapeCommand{

    @NotBlank
    @Positive
    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
