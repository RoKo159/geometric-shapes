package pl.kurs.geometricshapes.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "type", "radius", "version", "createdBy", "createdAt", "lastModifiedAt", "lastModifiedBy", "area", "perimeter"})
public class CircleDto extends ShapesDto {

    private Long id;
    private double radius;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
