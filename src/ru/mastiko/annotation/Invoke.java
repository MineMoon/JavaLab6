package ru.mastiko.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация-маркер для автоматического вызова методов.
 * Методы, помеченные этой аннотацией, будут автоматически вызваны обработчиком.
 *
 * @see ru.mastiko.processor.Processor#processInvoke(Object)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invoke {
}
