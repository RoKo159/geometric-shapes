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


    public Square() {
    }

    public Square(double[] parameters, String type, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy, double area, double perimeter, double width) {
        super(parameters, type, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy, area, perimeter);
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


    private void updateAreaAndPerimeter() {
        setArea(width * width);
        setPerimeter(4 * width);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Square square = (Square) o;
        return Double.compare(square.width, width) == 0 && Objects.equals(id, square.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, width);
    }

    @Override
    public String toString() {
        return "Square{" +
                "id=" + id +
                ", width=" + width +
                "} " + super.toString();
    }
}
