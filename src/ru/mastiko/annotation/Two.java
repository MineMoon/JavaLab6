package ru.mastiko.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация с двумя обязательными свойствами разных типов.
 * Демонстрирует аннотацию с несколькими обязательными параметрами.
 *
 * @see ru.mastiko.processor.Processor#processTwo(Class)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Two {
    String first();
    int second();
}
