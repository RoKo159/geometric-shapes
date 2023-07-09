package pl.kurs.geometricshapes.models;

import pl.kurs.geometricshapes.dto.CircleDto;
import pl.kurs.geometricshapes.dto.RectangleDto;
import pl.kurs.geometricshapes.dto.ShapesDto;
import pl.kurs.geometricshapes.dto.SquareDto;

public enum ShapeType {
    CIRCLE {
        @Override
        public Class<Circle> getShapeClass() {
            return Circle.class;
        }

        @Override
        public Class<CircleDto> getShapeDtoClass() {
            return CircleDto.class;
        }
    },
    RECTANGLE {
        @Override
        public Class<Rectangle> getShapeClass() {
            return Rectangle.class;
        }

        @Override
        public Class<RectangleDto> getShapeDtoClass() {
            return RectangleDto.class;
        }
    },
    SQUARE {
        @Override
        public Class<Square> getShapeClass() {
            return Square.class;
        }

        @Override
        public Class<SquareDto> getShapeDtoClass() {
            return SquareDto.class;
        }
    };

    public abstract Class<? extends Shapes> getShapeClass();
    public abstract Class<? extends ShapesDto> getShapeDtoClass();
}