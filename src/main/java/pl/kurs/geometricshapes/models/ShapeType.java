package pl.kurs.geometricshapes.models;

import pl.kurs.geometricshapes.dto.CircleDto;
import pl.kurs.geometricshapes.dto.RectangleDto;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;

import java.lang.reflect.Type;

public enum ShapeType {
    CIRCLE {
        @Override
        public Type getShapeClass() {
            return Circle.class;
        }

        @Override
        public Class<CircleDto> getShapeDtoClass() {
            return CircleDto.class;
        }
    },
    RECTANGLE {
        @Override
        public Type getShapeClass() {
            return Rectangle.class;
        }

        @Override
        public Class<RectangleDto> getShapeDtoClass() {
            return RectangleDto.class;
        }
    },
    SQUARE {
        @Override
        public Type getShapeClass() {
            return Square.class;
        }

        @Override
        public Class<SquareDto> getShapeDtoClass() {
            return SquareDto.class;
        }
    };

    public abstract Type getShapeClass();
    public abstract Class<? extends ShapesDto> getShapeDtoClass();
}
