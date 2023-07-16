package pl.kurs.geometricshapes.models;

import pl.kurs.geometricshapes.services.Identificationable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@MappedSuperclass
public abstract class Shapes implements Serializable, Identificationable {


    @Transient
    private double[] parameters;

    private String type;

    private String version;

    private String createdBy;

    private LocalDate createdAt;

    private LocalDate lastModifiedAt;

    private String lastModifiedBy;

    private double area;

    private double perimeter;

    public Shapes() {
    }

    public Shapes(double[] parameters, String type, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        this.parameters = parameters;
        this.type = type;
        this.version = version;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
        this.area = area;
        this.perimeter = perimeter;
    }

    public double[] getParameters() {
        return parameters;
    }

    public void setParameters(double[] parameters) {
        this.parameters = parameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shapes shapes = (Shapes) o;
        return Double.compare(shapes.area, area) == 0 && Double.compare(shapes.perimeter, perimeter) == 0 && Arrays.equals(parameters, shapes.parameters) && Objects.equals(type, shapes.type) && Objects.equals(version, shapes.version) && Objects.equals(createdBy, shapes.createdBy) && Objects.equals(createdAt, shapes.createdAt) && Objects.equals(lastModifiedAt, shapes.lastModifiedAt) && Objects.equals(lastModifiedBy, shapes.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy, area, perimeter);
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    @Override
    public String toString() {
        return "Shapes{" +
                "parameters=" + Arrays.toString(parameters) +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", area=" + area +
                ", perimeter=" + perimeter +
                '}';
    }
}
