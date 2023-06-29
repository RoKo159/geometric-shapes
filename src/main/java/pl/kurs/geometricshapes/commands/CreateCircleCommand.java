package pl.kurs.geometricshapes.commands;


public class CreateCircleCommand extends CreateShapeCommand{

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
