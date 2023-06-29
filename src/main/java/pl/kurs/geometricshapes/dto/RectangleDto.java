package pl.kurs.geometricshapes.dto;

import pl.kurs.geometricshapes.models.ShapeType;

public class RectangleDto extends ShapesDto {

    private double length;
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

    @Override
    public ShapeType getType() {
        return super.getType();
    }

    @Override
    public void setType(ShapeType type) {
        super.setType(type);
    }
}
