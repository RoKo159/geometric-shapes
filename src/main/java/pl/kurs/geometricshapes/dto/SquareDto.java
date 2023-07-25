package pl.kurs.geometricshapes.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "type", "width", "version", "createdBy", "createdAt", "lastModifiedAt", "lastModifiedBy", "area", "perimeter"})
public class SquareDto extends ShapesDto {

    private double width;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
