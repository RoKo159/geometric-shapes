package pl.kurs.geometricshapes.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "square")
public class Square extends Shapes {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_square")
    private Long id;

    private double width;
    private double area;
    private double perimeter;

    public Square() {
    }

    public Square(double[] parameters, String type, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double width) {
        super(parameters, type, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy);
        this.width = width;
    }

    @Override
    public void setParameters(double[] parameters) {
        super.setParameters(parameters);
        if(parameters != null && parameters.length > 0) {
            this.width = parameters[0];
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

    public double getWidth() {
        return width = width;
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
        this.area = width * width;
        this.perimeter = 4 * width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Square square = (Square) o;
        return Double.compare(square.width, width) == 0 && Double.compare(square.area, area) == 0 && Double.compare(square.perimeter, perimeter) == 0 && Objects.equals(id, square.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, width, area, perimeter);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", id=" + id +
                ", width=" + width +
                ", area=" + area +
                ", perimeter=" + perimeter +
                "} ";
    }
}
