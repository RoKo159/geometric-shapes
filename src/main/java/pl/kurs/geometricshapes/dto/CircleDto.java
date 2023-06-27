package pl.kurs.geometricshapes.dto;


import pl.kurs.geometricshapes.models.ShapeType;


public class CircleDto extends ShapesDto {

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public ShapeType getType() {
        return ShapeType.CIRCLE;
    }
}
