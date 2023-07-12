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
    private double area;
    private double perimeter;

    public Rectangle() {
    }

    public Rectangle(double[] parameters, String type, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double length, double width) {
        super(parameters, type, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy);
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
        return Double.compare(rectangle.length, length) == 0 && Double.compare(rectangle.width, width) == 0 && Double.compare(rectangle.area, area) == 0 && Double.compare(rectangle.perimeter, perimeter) == 0 && Objects.equals(id, rectangle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, length, width, area, perimeter);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "id=" + id +
                ", length=" + length +
                ", width=" + width +
                ", area=" + area +
                ", perimeter=" + perimeter +
                "} " + super.toString();
    }
}
