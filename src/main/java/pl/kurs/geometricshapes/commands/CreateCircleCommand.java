package pl.kurs.geometricshapes.commands;

public class CreateCircleCommand extends CreateShapeCommand{

    private double radius;


    public double getRadius() {
        double[] parameters = getParameters();
        return radius = getParameters()[0];
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        double[] parameters = getParameters();
        double radius = parameters[0];
        return Math.PI * radius * radius;
    }
    @Override
    public double getPerimeter() {
        double[] parameters = getParameters();
        double radius = parameters[0];
        return 2 * Math.PI * radius;
    }
}
