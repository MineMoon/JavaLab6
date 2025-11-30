package ru.mastiko.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ru.mastiko.person.Person;
import ru.mastiko.processor.Processor;
import ru.mastiko.userinput.*;



public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserInput input = new UserInput();

        int choice = 2;
        int choiceTask = 0;
        int argument = 0;
        boolean exit = false;


        Person Misha = new Person("Misha", 19,"Mikle Jordan");

        do{

            if(choice == 2){
                choiceTask = input.inputChoiceInt(1, 6,
                        "Выберите аннотацию для тестирования:\n" +
                                "1. @Invoke\n" +
                                "2. @Default\n" +
                                "3. @ToString\n" +
                                "4. @Validate\n" +
                                "5. @Two\n" +
                                "6. @Cache\n"
                );
            }

            switch (choiceTask) {

                case 1:
                    System.out.println("== Invoke ==");
                    System.out.println("Методы в классе Person:\n" +
                            "1. greetings() - помечен аннотацией @Invoke\n" +
                            "2. farewell() - не помечен аннотацией\n" +
                            "3. toString - не помечен аннотацией\n\n" +
                            "Должен вызваться только метод greetings():\n");
                    Processor.processInvoke(Misha);
                    break;
                case 2:
                    System.out.println("== Default ==");
                    System.out.println("Аннотация @Default на классе Person:\n" +
                            "Указывает тип по умолчанию: String\n\n" +
                            "Результат обработки:");
                    Processor.processDefaultClass(Misha.getClass());
                    break;

                case 3:
                    System.out.println("== ToString ==");
                    System.out.println("Поля класса Person:\n" +
                            "1. name - @ToString(Mode.YES) (включается)\n" +
                            "2. pastName - @ToString(Mode.NO) (исключается)\n" +
                            "3. age - без аннотации (включается)\n\n" +
                            "Строковое представление:");
                    System.out.println(Processor.processToString(Misha));

                    break;

                case 4:
                    System.out.println("== Validate ==");
                    System.out.println("Аннотация @Validate содержит классы:\n" +
                            "Integer, String, Double, Boolean, Double\n\n" +
                            "Результат (должны быть удалены дубликаты):");
                    Processor.processValidate(Misha.getClass());
                    break;

                case 5:
                    System.out.println("== Two ==");
                    System.out.println("Аннотация @Two с параметрами:\n" +
                            "first = \"Legend\", second = 19\n\n" +
                            "Извлеченные значения:");
                    Processor.processTwo(Misha.getClass());
                    break;

                case 6:
                    System.out.println("== Cache ==");
                    System.out.println("Аннотация @Cache с областями:\n" +
                            "users, orders, products\n\n" +
                            "Список областей кеширования:");

                    Processor.processCache(Misha.getClass());

                    break;
            }
            System.out.println();

            System.out.println("Повторить?\n1. Да\n2. К выбору задания\n3. Выход");
            choice = input.inputDiaposonInt(1, 3, "вариант");
            if(choice == 3){
                exit = true;
            }
            System.out.println();

        }while (!exit);
    }

}
