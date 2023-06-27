package pl.kurs.geometricshapes.dto;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import pl.kurs.geometricshapes.commands.CreateCircleCommand;
import pl.kurs.geometricshapes.commands.CreateRectangleCommand;
import pl.kurs.geometricshapes.commands.CreateSquareCommand;
import pl.kurs.geometricshapes.models.ShapeType;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateRectangleCommand.class, name = "RECTANGLE"),
        @JsonSubTypes.Type(value = CreateSquareCommand.class, name = "SQUARE"),
        @JsonSubTypes.Type(value = CreateCircleCommand.class, name = "CIRCLE")
})

public abstract class ShapesDto {

    private Long id;
    private ShapeType type;
    private String version;
    private String createdBy;
    private LocalDate createdAt;
    private LocalDate lastModifiedAt;
    private String lastModifiedBy;
    private double area;
    private double perimeter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDate lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }
}
