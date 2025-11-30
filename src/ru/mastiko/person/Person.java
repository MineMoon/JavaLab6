package ru.mastiko.person;

import ru.mastiko.annotation.*;
import ru.mastiko.enums.Mode;
import ru.mastiko.processor.Processor;

/**
 * Класс Person представляет сущность человека с демонстрацией всех кастомных аннотаций.
 * Используется для тестирования обработки аннотаций через Reflection API.
 *
 * @see Default
 * @see ToString
 * @see Validate
 * @see Two
 * @see Cache
 * @see Invoke
 */

@Default(String.class)
@ToString(Mode.YES)
@Validate({Integer.class, String.class, Double.class, Boolean.class})
@Two(first = "Legend", second = 19)
@Cache({"users", "orders", "products"})
public class Person {
    @ToString(Mode.YES)
    private String name;

    @ToString(Mode.NO)
    private String pastName;

    private int age;

    //properties
    public String getName() {
        return new String(name);
    }
    public String getPastName() {
        return new String(pastName);
    }

    public void setName(String name) {
        pastName = new String(name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    //constructors
    public Person(String name) {
        this.name = name;
        this.pastName = null;
        this.age = 0;
    }

    public Person(String name, int setAge ,String pastName) {
        this.name = name;
        this.age = setAge;
        this.pastName = pastName;
    }

    //methods
    @Invoke
    public void greetings(){
        System.out.println(this.name + ": Hello!");
    }

    public void farewell(){
        System.out.println(this.name + ": Goodbye!");
    }

    @Override
    public String toString() {
        return Processor.processToString(this);
    }
}
