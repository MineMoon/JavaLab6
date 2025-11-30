package ru.mastiko.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания областей кеширования.
 * Используется для пометки классов, которые должны кешироваться в указанных областях.
 *
 * @see ru.mastiko.processor.Processor#processCache(Class)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    String[] value() default {};
}
