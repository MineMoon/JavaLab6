package ru.mastiko.test;

import org.junit.jupiter.api.Test;
import ru.mastiko.annotation.ToString;
import ru.mastiko.enums.Mode;
import ru.mastiko.processor.Processor;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для демонстрации работы аннотации @ToString.
 * Содержит поля с различными вариантами аннотации ToString.
 * @see ToString
 */
@ToString
class ClassToStringTest {
    String field1 = "field1";
    int field2 = 42;

    @ToString(Mode.NO)
    String field3 = "field3";

    @ToString(Mode.YES )
    String field4 = "field4";

    @ToString
    double field5 = 52.52;
}

/**
 * Тестовый класс с аннотацией ToString(Mode.NO) на уровне класса.
 * Демонстрирует поведение когда весь класс исключен из строкового представления.
 * @see ToString
 */
@ToString(Mode.NO)
class ClassToStringNoMode {
    String field1 = "field1";
    int field2 = 42;
}

/**
 * Тестовый класс для проверки работы метода processToString.
 * Содержит тесты для различных сценариев использования аннотации ToString.
 * @see ToString
 * @see ru.mastiko.processor.Processor#processToString(Object)
 */
class ProcessoToStringTest {

    /**
     * Тестирует включение и исключение полей в строковое представление.
     * Проверяет что поля без аннотации и с Mode.YES включаются, а с Mode.NO - исключаются.
     * @see ToString
     * @see ru.mastiko.processor.Processor#processToString(Object)
     */
    @Test
    void testToString() {
        Object obj = new ClassToStringTest();
        String result = Processor.processToString(obj);
        // System.out.println(result);
        assertAll(
                () -> assertTrue(result.contains("field1")),
                () -> assertTrue(result.contains("field2")),
                () -> assertFalse(result.contains("field3")),
                () -> assertTrue(result.contains("field4")),
                () -> assertTrue(result.contains("field5"))
        );
    }

    /**
     * Тестирует поведение когда класс помечен аннотацией ToString(Mode.NO).
     * Проверяет что возвращается специальное сообщение.
     * @see ToString
     * @see ru.mastiko.processor.Processor#processToString(Object)
     */
    @Test
    void testToStringClassNoMode() {
        Object obj = new ClassToStringNoMode();
        String result = Processor.processToString(obj);
        // System.out.println(result);
        assertEquals("The class is annotated with a notation having the NO mode", result);
    }

    /**
     * Тестирует обработку null объекта.
     * Проверяет что выбрасывается NullPointerException с правильным сообщением.
     * @see ToString
     * @see ru.mastiko.processor.Processor#processToString(Object)
     */
    @Test
    void testToStringNull(){
        try{
            Processor.processToString(null);
        } catch (NullPointerException e){
            assertEquals("object is null", e.getMessage());
        }
    }


}