package ru.mastiko.test;

import ru.mastiko.annotation.Validate;
import org.junit.jupiter.api.Test;
import ru.mastiko.processor.Processor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * Тестовый класс для демонстрации работы аннотации @Validate.
 * Содержит дубликаты классов для проверки их удаления при обработке.
 * @see Validate
 */
@Validate({Integer.class, String.class, Double.class, Boolean.class, String.class})
class ClassValidateTest {
}

/**
 * Тестовый класс с пустым массивом в аннотации @Validate.
 * Используется для проверки выбрасывания исключения.
 *@see Validate
 */
@Validate({})
class ClassValidateEmptyTest {
}

/**
 * Тестовый класс для проверки работы метода processValidate.
 * Содержит тесты для проверки обработки аннотации Validate.
 * @see Validate
 * @see ru.mastiko.processor.Processor#processValidate(Class)
 */
class ProcessorValidateTest {
    /**
     * Тестирует корректную обработку аннотации Validate и удаление дубликатов.
     * Проверяет что возвращаемый список содержит только уникальные классы.
     * @see Validate
     * @see ru.mastiko.processor.Processor#processValidate(Class)
     */
    @Test
    void testValidate() {
        List<Class<?>> result = Processor.processValidate(ClassValidateTest.class);
        List<Class<?>> expected = Arrays.asList(Integer.class, String.class, Double.class, Boolean.class);
        assertEquals(expected, result);
    }

    /**
     * Тестирует обработку null значения.
     * Проверяет что выбрасывается NullPointerException с правильным сообщением.
     * @see Validate
     * @see ru.mastiko.processor.Processor#processValidate(Class)
     */
    @Test
    void testValidateNull() {
        try{
            Processor.processValidate(null);
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "someClass is null");
        }
    }

    /**
     * Тестирует обработку аннотации Validate с пустым массивом классов.
     * Проверяет что выбрасывается IllegalArgumentException с правильным сообщением.
     * @see Validate
     * @see ru.mastiko.processor.Processor#processValidate(Class)
     */
    @Test
    void testValidateEmptyArray() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Processor.processValidate(ClassValidateEmptyTest.class));
        assertEquals("The array of classes in @Validate annotation cannot be empty", exception.getMessage());
    }
}