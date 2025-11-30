package ru.mastiko.annotation;

import ru.mastiko.enums.Mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для управления включением полей в строковое представление объекта.
 * Определяет, какие поля должны включаться в результат метода toString.
 *
 * @see ru.mastiko.processor.Processor#processToString(Object)
 * @see ru.mastiko.enums.Mode
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToString {
    Mode value() default Mode.YES;
}
