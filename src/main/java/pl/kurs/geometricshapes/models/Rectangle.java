package pl.kurs.geometricshapes.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "rectangle")
public class Rectangle extends Shapes {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rectangle")
    private Long id;

    private double length;

    private double width;

    public Rectangle() {
    }

    public Rectangle(double length, double width, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        super(version, createdBy, createdAt, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.length = length;
        this.width = width;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.length, length) == 0 && Double.compare(rectangle.width, width) == 0 && Objects.equals(id, rectangle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, length, width);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "id=" + id +
                ", length=" + length +
                ", width=" + width +
                "} " + super.toString();
    }

    @Override
    public double getArea() {
        return length * width;
    }

    @Override
    public double getPerimeter() {
        return 2 * length + 2 * width;
    }


}
