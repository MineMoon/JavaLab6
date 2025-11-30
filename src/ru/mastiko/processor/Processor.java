package ru.mastiko.processor;

import ru.mastiko.annotation.*;
import ru.mastiko.enums.Mode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import ru.mastiko.Mpair.*;

/**
 * Класс-обработчик для работы с кастомными аннотациями.
 * Содержит методы для обработки различных типов аннотаций через Reflection API.
 */
public class Processor {
    /**
     * Выполняет все методы, помеченные аннотацией @Invoke.
     *
     * @param object объект, у которого вызываются методы с аннотацией Invoke
     * @throws NullPointerException если передан null объект
     * @see Invoke
     */
    public static void processInvoke(Object object) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        for (Method method : object.getClass().getDeclaredMethods()) {
            Invoke inv = method.getAnnotation(Invoke.class);
            if (inv != null) {
                try {
                    method.setAccessible(true);
                    method.invoke(object);
                    System.out.println("The method was called: " + method.getName());
                } catch (Exception e) {
                    System.err.println("Error in the method: " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Обрабатывает аннотацию @Default на уровне класса.
     * Выводит информацию о классе по умолчанию, указанном в аннотации.
     *
     * @param someClass класс для анализа на наличие аннотации Default
     * @return Класс по умолчанию
     * @throws NullPointerException если передан null
     * @see Default
     */
    public static Class<?> processDefaultClass(Class<?> someClass) {
        if (someClass == null) {
            throw new NullPointerException("someClass is null");
        }
        Default def = someClass.getAnnotation(Default.class);
        if (def != null) {
            System.out.println("For "+ someClass.getSimpleName() + " The default class called: " + def.value().getSimpleName());
        } else {
            System.out.println("Annotation @Default not found");
        }
        return def.value();
    }

    /**
     * Обрабатывает аннотации @Default на полях класса.
     * Возвращает список полей, помеченных аннотацией Default.
     *
     * @param someClass класс для анализа полей на наличие аннотации Default
     * @return список полей с аннотацией Default
     * @throws NullPointerException если передан null
     * @see Default
     */
    public static List<Field> processDefaultFields(Class<?> someClass) {
        if (someClass == null) {
            throw new NullPointerException("someClass is null");
        }
        List<Field> fields = new ArrayList<Field>();
        for (Field field : someClass.getDeclaredFields()) {
            field.setAccessible(true);
            Default def = field.getAnnotation(Default.class);
            if (def != null) {
                fields.add(field);
                System.out.println("The default field called: " + def.value());
            }
        }
        return fields;
    }

    /**
     * Формирует и выводит строковое представление объекта на основе аннотации @ToString.
     * Включает только те поля, которые имеют аннотацию ToString(Mode.YES) или не аннотированы вовсе.
     * Исключает поля с аннотацией ToString(Mode.NO).
     * Также не обрабатывает класы, если они помечены анотацией ToString(Mode.NO).
     *
     * @param object объект для строкового представления
     * @return строковое представление объекта
     * @throws NullPointerException если передан null объект
     * @see ru.mastiko.enums.Mode
     * @see ToString
     */
    public static String processToString(Object object) {
        if (object == null) {
            throw new NullPointerException("object is null");
        }
        ToString toS = object.getClass().getAnnotation(ToString.class);
        if (toS == null) {
            return object.toString();
        }
        if(toS.value()==Mode.NO){
            return "The class is annotated with a notation having the NO mode";
        }


        Class<?> someClass = object.getClass();
        StringBuilder result = new StringBuilder();
        result.append("{\n");

        for (Field field: someClass.getDeclaredFields()) {
            field.setAccessible(true);
            toS = field.getAnnotation(ToString.class);
            if (toS == null || toS.value() == Mode.YES) {
                try {
                    result.append(field.getName()).append(" = ").append(field.get(object)).append(",\n");
                } catch (IllegalAccessException e) {
                    result.append(field.getName()).append("=N/A,\n");
                }
            }
        }
        result.delete(result.length()-2, result.length());
        result.append("\n}");
        return result.toString();
    }

    /**
     * Обрабатывает аннотацию @Validate и возвращает список классов для валидации.
     * Убирает дубликаты классов из результата.
     *
     * @param someClass класс для анализа на наличие аннотации Validate
     * @return список классов для валидации без дубликатов
     * @throws NullPointerException если передан null
     * @see Validate
     */
    public static List<Class<?>> processValidate(Class<?> someClass) {
        if (someClass == null) {
            throw new NullPointerException("someClass is null");
        }

        Validate valid = someClass.getAnnotation(Validate.class);
        Set<Class<?>> resultSet = new LinkedHashSet<>(); // Сохраняем порядок

        if (valid != null) {
            if (valid.value().length == 0) {
                throw new IllegalArgumentException("The array of classes in @Validate annotation cannot be empty");
            }
            Class<?>[] classes = valid.value();


            for (Class<?> tempClass : classes) {
                if (tempClass != null) {
                    resultSet.add(tempClass); // Set автоматически убирает дубликаты
                }
            }

            String strOutput="[";
            for (Class<?> tempClass : resultSet) {
                strOutput += tempClass.getSimpleName() + ",";
            }
            strOutput = strOutput.substring(0, strOutput.length()-1);
            strOutput += "]";
            System.out.println("Class " + someClass.getSimpleName() + " is annotated with @Validate\n"+ strOutput);

        }

        return new ArrayList<>(resultSet);
    }

    /**
     * Обрабатывает аннотацию @Two и возвращает значения её свойств в виде пары.
     * Извлекает значения параметров first (String) и second (int) из аннотации @Two.
     *
     * @param someClass класс для анализа на наличие аннотации Two
     * @return пара значений (first, second) из аннотации @Two, или пустая пара ("", 0) если аннотация не найдена
     * @throws NullPointerException если передан null
     * @see Two
     */
    public static Pair<String, Integer> processTwo(Class<?> someClass) {
        if (someClass == null) {
            throw new NullPointerException("someClass is null");
        }

        Two two = someClass.getAnnotation(Two.class);
        if (two != null) {
            String str = two.first();
            int num = two.second();
            System.out.println("Class: " + someClass.getSimpleName() + "\nFirst: " + str + ", Second: " + num);
            return new Pair<>(str, num);
        } else {
            System.out.println("Аннотация @Two не найдена в классе: " + someClass.getSimpleName());
            return new Pair<>("", 0);
        }
    }

    /**
     * Обрабатывает аннотацию @Cache и возвращает список кешируемых областей.
     * Убирает дубликаты и пустые строки из результата.
     *
     * @param someClass класс для анализа на наличие аннотации Cache
     * @return список названий областей кеширования без дубликатов
     * @throws NullPointerException если передан null
     * @see Cache
     */
    public static List<String> processCache(Class<?> someClass) {
        if (someClass == null) {
            throw new NullPointerException("someClass is null");
        }

        Cache cache = someClass.getAnnotation(Cache.class);
        Set<String> resultSet = new LinkedHashSet<>(); // Сохраняем порядок и убираем дубликаты

        if (cache != null) {
            String[] cacheAreas = cache.value();

            System.out.println("Class " + someClass.getSimpleName() + " is annotated with @Cache have areas [");
            for (String area : cacheAreas) {
                if (area != null && !area.trim().isEmpty()) {
                    resultSet.add(area);
                    System.out.println(area);
                }
            }
            System.out.println("]");
        }

        return new ArrayList<>(resultSet);
    }

}
