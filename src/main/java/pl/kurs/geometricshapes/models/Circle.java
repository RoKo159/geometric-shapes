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

    public Circle() {
    }


    public Circle(double[] parameters, String type, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double area, double perimeter, double radius) {
        super(parameters, type, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy, area, perimeter);
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


    private void updateAreaAndPerimeter() {
        setArea(Math.PI * radius * radius);
        setPerimeter(2 * Math.PI * radius);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0 && Objects.equals(id, circle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, radius);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + id +
                ", radius=" + radius +
                "} " + super.toString();
    }
}

