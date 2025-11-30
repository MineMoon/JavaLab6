package ru.mastiko.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания типа по умолчанию.
 * Может применяться к классам и полям для определения типа по умолчанию.
 *
 * @see ru.mastiko.processor.Processor#processDefaultClass(Class)
 * @see ru.mastiko.processor.Processor#processDefaultFields(Class)
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Default {
    Class<?> value();
}
