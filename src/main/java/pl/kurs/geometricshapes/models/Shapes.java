package pl.kurs.geometricshapes.models;

import pl.kurs.geometricshapes.services.Identificationable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
public abstract class Shapes implements Serializable, Identificationable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shape")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ShapeType type;

    private String version;

    private String createdBy;

    private LocalDate createdAt;

    private LocalDate lastModifiedAt;

    private String lastModifiedBy;

    public Shapes() {
    }

    public Shapes(Long id, ShapeType type, String version, String createdBy, LocalDate createdAt, LocalDate lastModifiedAt, String lastModifiedBy) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShapeType getType() {
        return type;
    }

    public void setType(ShapeType shapeType) {
        this.type = shapeType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDate lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shapes shapes = (Shapes) o;
        return Objects.equals(id, shapes.id) && type == shapes.type && Objects.equals(version, shapes.version) && Objects.equals(createdBy, shapes.createdBy) && Objects.equals(createdAt, shapes.createdAt) && Objects.equals(lastModifiedAt, shapes.lastModifiedAt) && Objects.equals(lastModifiedBy, shapes.lastModifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, version, createdBy, createdAt, lastModifiedAt, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "Shapes{" +
                "id=" + id +
                ", shapeType=" + type +
                ", version='" + version + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }

    public abstract double getArea();

    public abstract double getPerimeter();
}
