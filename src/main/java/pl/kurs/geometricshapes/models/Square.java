package pl.kurs.geometricshapes.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "square")
public class Square extends Shapes {
    private static final long serialVersionUID = 1L;

    private double width;
    private double area;
    private double perimeter;

    public Square() {
    }

    public Square(Long id, ShapeType shapeType, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double width) {
        super(id, shapeType, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy);
        this.width = width;
        updateAreaAndPerimeter();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double length) {
        this.width = length;
        updateAreaAndPerimeter();
    }

    public double getArea() {
        return this.area;
    }

    public double getPerimeter() {
        return this.perimeter;
    }

    private void updateAreaAndPerimeter() {
        this.area = width * width;
        this.perimeter = 4 * width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Square square = (Square) o;
        return Double.compare(square.width, width) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width);
    }

    @Override
    public String toString() {
        return "Square{" +
                "length=" + width +
                ", area=" + area +
                ", perimeter=" + perimeter +
                "} " + super.toString();
    }
}
