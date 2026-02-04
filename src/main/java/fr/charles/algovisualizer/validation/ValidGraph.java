package fr.charles.algovisualizer.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidGraphValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGraph {
    String message() default "Invalid graph structure";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
