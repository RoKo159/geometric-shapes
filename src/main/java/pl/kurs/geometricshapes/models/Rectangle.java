package pl.kurs.geometricshapes.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "rectangle")
public class Rectangle extends Shapes {
    private static final long serialVersionUID = 1L;

    private double length;
    private double width;
    private double area;
    private double perimeter;

    public Rectangle() {
    }


    public Rectangle(Long id, ShapeType type, double[] parameters, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double length, double width) {
        super(id, type, parameters, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy);
        this.length = length;
        this.width = width;
    }

    @Override
    public void setParameters(double[] parameters) {
        super.setParameters(parameters);
        if(parameters != null && parameters.length > 0) {
            this.width = parameters[0];
            this.length = parameters[1];
        }
        updateAreaAndPerimeter();
    }

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

    public double getArea() {
        return this.area;
    }

    public double getPerimeter() {
        return this.perimeter;
    }

    private void updateAreaAndPerimeter() {
        this.area = length * width;
        this.perimeter = 2 * (length + width);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.length, length) == 0 && Double.compare(rectangle.width, width) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length, width);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "length=" + length +
                ", width=" + width +
                ", area=" + area +
                ", perimeter=" + perimeter +
                "} " + super.toString();
    }
}
