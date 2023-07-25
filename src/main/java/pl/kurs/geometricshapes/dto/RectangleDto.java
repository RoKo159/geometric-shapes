package pl.kurs.geometricshapes.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "type", "width", "length", "version", "createdBy", "createdAt", "lastModifiedAt", "lastModifiedBy", "area", "perimeter"})
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
}
