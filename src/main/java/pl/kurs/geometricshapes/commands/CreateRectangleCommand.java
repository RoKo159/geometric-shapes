package pl.kurs.geometricshapes.commands;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CreateRectangleCommand extends CreateShapeCommand {

    @NotBlank
    @Positive
    private double length;

    @NotBlank
    @Positive
    private double width;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

}
