package pl.kurs.geometricshapes.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = ShapeTypeValidator.class)
@Target({FIELD, PARAMETER, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedShapeType {

    String message() default "Not supported shape! Supported shapes are: {supportedShapeType}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

