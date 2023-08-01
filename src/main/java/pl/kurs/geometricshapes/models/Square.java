package pl.kurs.geometricshapes.models;
import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue("SQUARE")
public class Square extends Shape {
    private Double width;

    public Square() {
    }

    public Square(Double width) {
        this.width = width;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(width, square.width);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width);
    }

    @Override
    public String toString() {
        return "Square{" +
                "width=" + width +
                "} " + super.toString();
    }

    @Override
    public Double getArea() {
        return width * width;
    }

    @Override
    public Double getPerimeter() {
        return 4 * width;
    }
}
