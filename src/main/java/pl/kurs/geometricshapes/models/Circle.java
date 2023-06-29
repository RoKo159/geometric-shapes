package pl.kurs.geometricshapes.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "circle")
public class Circle extends Shapes {
    private static final long serialVersionUID = 1L;

    private double radius;
    private double area;
    private double perimeter;

    public Circle() {
    }

    public Circle(Long id, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double radius) {
        super(id, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy);
        this.radius = radius;
        updateAreaAndCircumference();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        updateAreaAndCircumference();
    }

    public double getArea() {
        return this.area;
    }

    public double getPerimeter() {
        return this.perimeter;
    }

    private void updateAreaAndCircumference() {
        this.area = Math.PI * radius * radius;
        this.perimeter = 2 * Math.PI * radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                ", area=" + area +
                ", circumference=" + perimeter +
                "} " + super.toString();
    }
}