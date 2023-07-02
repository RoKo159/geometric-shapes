package pl.kurs.geometricshapes.commands;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CreateSquareCommand extends CreateShapeCommand {

    @NotBlank
    @Positive
    private double width;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

}
