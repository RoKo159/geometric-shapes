package pl.kurs.geometricshapes.commands;


public class CreateRectangleCommand extends CreateShapeCommand {

    private double length;
    private double width;

    public double getLength() {
        double[] parameters = getParameters();
        return length = getParameters()[1];
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        double[] parameters = getParameters();
        return width = getParameters()[0];
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getArea() {
        double[] parameters = getParameters();
        double width = parameters[0];
        double length = parameters[1];
        return length * width;
    }

    @Override
    public double getPerimeter() {
        double[] parameters = getParameters();
        double width = parameters[0];
        double length = parameters[1];
        return 2 * length + 2 * width;
    }
}
