package pl.kurs.geometricshapes.validators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ShapeTypeValidator implements ConstraintValidator<SupportedShapeType, String> {

    private List<String> supportedShapeType;

    @Override
    public void initialize(SupportedShapeType constraintAnnotation) {
        supportedShapeType = List.of("square", "circle", "rectangle");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!supportedShapeType.contains(s.trim().toLowerCase())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Not supported shape! Supported shapes are: " + String.join(", ", supportedShapeType))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
