package pl.kurs.geometricshapes.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "type", "radius", "version", "createdBy", "createdAt", "lastModifiedAt", "lastModifiedBy", "area", "perimeter"})
public class CircleDto extends ShapesDto {

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
