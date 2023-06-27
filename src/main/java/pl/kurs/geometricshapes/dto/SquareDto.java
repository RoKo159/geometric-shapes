package pl.kurs.geometricshapes.dto;

import lombok.Data;
import pl.kurs.geometricshapes.models.ShapeType;


public class SquareDto extends ShapesDto {

    private double width;


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public ShapeType getType() {
        return ShapeType.SQUARE;
    }
}
