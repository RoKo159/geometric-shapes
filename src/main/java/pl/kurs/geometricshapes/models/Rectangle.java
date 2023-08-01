package pl.kurs.geometricshapes.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue("RECTANGLE")
public class Rectangle extends Shape {

    private Double width;
    private Double length;

    public Rectangle() {
    }

    public Rectangle(Double width, Double length) {
        this.width = width;
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(width, rectangle.width) && Objects.equals(length, rectangle.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, length);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", length=" + length +
                "} " + super.toString();
    }

    @Override
    public Double getArea() {
        return width * length;
    }

    @Override
    public Double getPerimeter() {
        return 2 * width + 2 * length;
    }
}