package pl.kurs.geometricshapes.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "circle")
public class Circle extends Shapes {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_circle")
    private Long id;
    private double radius;
    private double area;
    private double perimeter;

    public Circle() {
    }


    public Circle(double[] parameters, String type, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, Long id, double radius) {
        super(parameters, type, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy);
        this.id = id;
        this.radius = radius;
    }

    @Override
    public void setParameters(double[] parameters) {
        super.setParameters(parameters);
        if(parameters != null && parameters.length > 0) {
            this.radius = parameters[0];
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getArea() {
        return this.area;
    }

    public double getPerimeter() {
        return this.perimeter;
    }

    private void updateAreaAndPerimeter() {
        this.area = Math.PI * radius * radius;
        this.perimeter = 2 * Math.PI * radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0 && Double.compare(circle.area, area) == 0 && Double.compare(circle.perimeter, perimeter) == 0 && Objects.equals(id, circle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, radius, area, perimeter);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", radius=" + radius +
                ", area=" + area +
                ", perimeter=" + perimeter +
                "} " + super.toString();
    }
}

