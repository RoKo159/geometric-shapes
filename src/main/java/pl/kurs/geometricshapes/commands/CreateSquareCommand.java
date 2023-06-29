package pl.kurs.geometricshapes.commands;


public class CreateSquareCommand extends CreateShapeCommand {

    private double width;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

}
