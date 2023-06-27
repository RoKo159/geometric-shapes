package pl.kurs.geometricshapes.commands;


public class CreateSquareCommand extends CreateShapeCommand{

    private double width;


    public double getWidth() {
        double[] parameters = getParameters();
        return width = parameters[0];
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getArea() {
        double[] parameters = getParameters();
        double width = parameters[0];
        return width * width;
    }

    @Override
    public double getPerimeter() {
        double[] parameters = getParameters();
        double width = parameters[0];
        return 4 * width;
    }
}
