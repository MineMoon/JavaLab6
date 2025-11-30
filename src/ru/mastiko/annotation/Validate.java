package ru.mastiko.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания классов, которые должны использоваться при валидации.
 * Может применяться к классам и другим аннотациям.
 *
 * @see ru.mastiko.processor.Processor#processValidate(Class)
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    Class<?>[] value();
}
