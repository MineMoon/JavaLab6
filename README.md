# Лабораторная работа Java №6

## Содержание
1. [Аннотации](#аннотации)
2. [Класс Processor](#класс-processor)
3. [Аннотация @Invoke](#аннотация-invoke)
4. [Аннотация @Default](#аннотация-default)
5. [Аннотация @ToString](#аннотация-tostring)
6. [Аннотация @Validate](#аннотация-validate)
7. [Аннотация @Two](#аннотация-two)
8. [Аннотация @Cache](#аннотация-cache)
9. [Тестирование](#тестирование)
10. [Пакет userinput](#пакет-userinput)
    - [Класс UserInput](#класс-userinput)
    - [Класс Check](#класс-check)
11. [Пакет Mpair](#пакет-mpair)
    - [Класс Pair](#класс-pair)
## Аннотации

### Перечень созданных аннотаций:
- **@Invoke** - маркер для автоматического вызова методов
- **@Default** - указание типа по умолчанию
- **@ToString** - управление строковым представлением объектов
- **@Validate** - указание классов для валидации
- **@Two** - аннотация с двумя обязательными параметрами
- **@Cache** - указание областей кеширования

## Класс Processor

Основной класс-обработчик, содержащий статические методы для работы с кастомными аннотациями через Reflection API.

## Аннотация @Invoke

### Назначение
Маркерная аннотация для автоматического вызова методов помеченных классов.

### Алгоритм обработки (processInvoke)

1. **Проверка входных данных** - валидация null объекта
```java
if (object == null) {
    throw new NullPointerException("Object is null");
}
```

2. **Сканирование методов** - получение всех методов класса через reflection
```java
for (Method method : object.getClass().getDeclaredMethods()) {
    Invoke inv = method.getAnnotation(Invoke.class);
```

3. **Вызов помеченных методов**
```java
method.invoke(object);
```

4. **Обработка исключений** - перехват ошибок выполнения

**Особенности:** Поддерживает вызов private методов через `setAccessible(true)`


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/Invoke1.png)

## Аннотация @Default

### Назначение
Указание типа по умолчанию для классов и полей.

### Алгоритм обработки (processDefaultClass)

1. **Получение аннотации** - извлечение @Default с класса
```java
Default def = someClass.getAnnotation(Default.class);
```

2. **Возврат значения** - возврат класса, указанного в аннотации

### Алгоритм обработки (processDefaultFields)

1. **Сканирование полей** - итерация по всем полям класса
```java
for (Field field : someClass.getDeclaredFields()) {
    field.setAccessible(true);
```

2. **Фильтрация по аннотации** - отбор полей с @Default
```java
Default def = field.getAnnotation(Default.class);
if (def != null) {
    fields.add(field);
}
```

**Особенности:** Работает как на уровне класса, так и на уровне полей


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/Default1.png)

## Аннотация @ToString

### Назначение
Управление включением полей в строковое представление объектов.

### Алгоритм обработки (processToString)

1. **Проверка аннотации класса** - определение глобального поведения
```java
ToString toS = object.getClass().getAnnotation(ToString.class);
if(toS.value()==Mode.NO){
    return "The class is annotated with a notation having the NO mode";
}
```

2. **Анализ полей** - проверка аннотации каждого поля
```java
toS = field.getAnnotation(ToString.class);
if (toS == null || toS.value() == Mode.YES) {
```

3. **Формирование результата** - построение строки с учетом фильтрации
```java
result.append(field.getName()).append(" = ").append(field.get(object)).append(",\n");
```

**Особенности:** Поддерживает режимы YES/NO для полей и классов (включать/не включать)


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/ToString1.png)

## Аннотация @Validate

### Назначение
Указание списка классов для валидации с автоматическим удалением дубликатов.

### Алгоритм обработки (processValidate)

1. **Валидация входных данных** - проверка null и пустого массива
```java
if (valid.value().length == 0) {
    throw new IllegalArgumentException("The array of classes in @Validate annotation cannot be empty");
}
```

2. **Удаление дубликатов** - использование LinkedHashSet для сохранения порядка
```java
Set<Class<?>> resultSet = new LinkedHashSet<>();
for (Class<?> tempClass : classes) {
    resultSet.add(tempClass);
}
```

3. **Форматирование вывода** - построение читаемого списка классов

**Особенности:** Автоматически удаляет дубликаты, сохраняет порядок элементов


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/Validate1.png)

## Аннотация @Two

### Назначение
Аннотация с двумя обязательными параметрами разных типов - строковым (`first`) и целочисленным (`second`). Демонстрирует работу с аннотациями, имеющими несколько обязательных параметров различных типов.

### Алгоритм обработки (processTwo)

1. **Проверка входных данных** - валидация null параметра
```java
if (someClass == null) {
    throw new NullPointerException("someClass is null");
}
```

2. **Извлечение аннотации** - получение аннотации @Two с класса через Reflection
```java
Two two = someClass.getAnnotation(Two.class);
```

3. **Извлечение параметров** - получение значений обоих обязательных параметров
```java
String str = two.first();
int num = two.second();
```

4. **Формирование результата** - возврат значений в виде типизированной пары
```java
return new Pair<>(str, num);
```

5. **Обработка отсутствия аннотации** - возврат пары с значениями по умолчанию и информационным сообщением

### Особенности реализации:
- **Типизированный возврат** - метод возвращает параметризованный объект `Pair<String, Integer>`
- **Защита от отсутствия аннотации** - корректная обработка случаев, когда аннотация отсутствует на классе
- **Сохранение информации о типах** - строгая типизация сохраняется через generics
- **Информативный вывод** - подробное сообщение при отсутствии аннотации


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/Two1.png)
## Аннотация @Cache

### Назначение
Указание областей кеширования для класса.

### Алгоритм обработки (processCache)

1. **Обработка массива строк** - извлечение областей кеширования
```java
String[] cacheAreas = cache.value();
```

2. **Фильтрация** - удаление пустых строк и дубликатов
```java
Set<String> resultSet = new LinkedHashSet<>();
for (String area : cacheAreas) {
    if (area != null && !area.trim().isEmpty()) {
        resultSet.add(area);
    }
}
```

3. **Форматированный вывод** - отображение списка областей

**Особенности:** Автоматическая очистка от некорректных значений


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/Cache1.png)

# Тестирование

## Тестирование аннотации @Validate

### Класс ProcessorValidateTest

#### Назначение
Комплексное тестирование обработки аннотации @Validate, включая нормальные случаи, граничные условия и обработку ошибок.

#### Алгоритм тестирования

**1. Тест корректной обработки с удалением дубликатов (testValidate)**
```java
@Test
void testValidate() {
    List<Class<?>> result = Processor.processValidate(ClassValidateTest.class);
    List<Class<?>> expected = Arrays.asList(Integer.class, String.class, Double.class, Boolean.class);
    assertEquals(expected, result);
}
```
- **Цель:** Проверить корректное извлечение классов и удаление дубликатов
- **Входные данные:** Класс с аннотацией `@Validate({Integer.class, String.class, Double.class, Boolean.class, String.class})`
- **Ожидаемый результат:** Список `[Integer, String, Double, Boolean]` (без дубликата String)
- **Проверяемые аспекты:**
    - Корректное чтение аннотации
    - Удаление дублирующихся классов
    - Возврат модифицируемого списка

**2. Тест обработки null значения (testValidateNull)**
```java
@Test
void testValidateNull() {
    try{
        Processor.processValidate(null);
    } catch (NullPointerException e) {
        assertEquals(e.getMessage(), "someClass is null");
    }
}
```
- **Цель:** Проверить корректную обработку null параметра
- **Входные данные:** `null`
- **Ожидаемый результат:** `NullPointerException` с сообщением "someClass is null"
- **Проверяемые аспекты:**
    - Защита от null входных данных
    - Информативность сообщения об ошибке
    - Корректный тип исключения

**3. Тест пустого массива классов (testValidateEmptyArray)**
```java
@Test
void testValidateEmptyArray() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> Processor.processValidate(ClassValidateEmptyTest.class));
    assertEquals("The array of classes in @Validate annotation cannot be empty", exception.getMessage());
}
```

assertThrows проверяет, тот ли exeption викинула лямда, и если все нормально, то возвращает тип exeption,
которую мы записываем в переменную exception, для того чтобы в будующем посмотреть сообщение.

- **Цель:** Проверить валидацию пустого массива в аннотации
- **Входные данные:** Класс с аннотацией `@Validate({})`
- **Ожидаемый результат:** `IllegalArgumentException` с соответствующим сообщением
- **Проверяемые аспекты:**
    - Проверка на пустой массив
    - Корректность типа исключения
    - Информативность сообщения об ошибке

### Тестовые классы-заглушки

**ClassValidateTest**
```java
@Validate({Integer.class, String.class, Double.class, Boolean.class, String.class})
class ClassValidateTest {
}
```
- Содержит преднамеренный дубликат (String.class) для проверки удаления повторений

**ClassValidateEmptyTest**
```java
@Validate({})
class ClassValidateEmptyTest {
}
```
- Специальный класс для тестирования обработки пустого массива


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/ProcessorValidateTest1.png)

## Тестирование аннотации @ToString

### Класс ProcessoToStringTest

#### Назначение
Тестирование сложного поведения аннотации @ToString, включая комбинации аннотаций на уровне класса и полей.

#### Алгоритм тестирования

**1. Тест включения/исключения полей (testToString)**
```java
@Test
void testToString() {
    Object obj = new ClassToStringTest();
    String result = Processor.processToString(obj);
    assertAll(
            () -> assertTrue(result.contains("field1")),
            () -> assertTrue(result.contains("field2")),
            () -> assertFalse(result.contains("field3")),
            () -> assertTrue(result.contains("field4")),
            () -> assertTrue(result.contains("field5"))
    );
}
```
- **Цель:** Проверить корректность фильтрации полей на основе аннотаций
- **Входные данные:** Объект ClassToStringTest с различными комбинациями аннотаций
- **Проверяемые сценарии:**
    - Поле без аннотации (field1) - должно включаться (по умолчанию YES)
    - Поле без аннотации (field2) - должно включаться
    - Поле с `@ToString(Mode.NO)` (field3) - должно исключаться
    - Поле с `@ToString(Mode.YES)` (field4) - должно включаться
    - Поле с `@ToString` (field5) - должно включаться (значение по умолчанию YES)

**2. Тест аннотации NO на уровне класса (testToStringClassNoMode)**
```java
@Test
void testToStringClassNoMode() {
    Object obj = new ClassToStringNoMode();
    String result = Processor.processToString(obj);
    assertEquals("The class is annotated with a notation having the NO mode", result);
}
```
- **Цель:** Проверить глобальное отключение toString для всего класса
- **Входные данные:** Класс с аннотацией `@ToString(Mode.NO)`
- **Ожидаемый результат:** Специальное сообщение вместо нормального представления
- **Проверяемые аспекты:**
    - Приоритет аннотации класса над аннотациями полей
    - Корректность возвращаемого сообщения

**3. Тест обработки null (testToStringNull)**
```java
@Test
void testToStringNull(){
    try{
        Processor.processToString(null);
    } catch (NullPointerException e){
        assertEquals("object is null", e.getMessage());
    }
}
```
- **Цель:** Проверить обработку null объекта
- **Ожидаемый результат:** `NullPointerException` с сообщением "object is null"

### Тестовые классы-заглушки

**ClassToStringTest**
```java
@ToString
class ClassToStringTest {
    String field1 = "field1";
    int field2 = 42;

    @ToString(Mode.NO)
    String field3 = "field3";

    @ToString(Mode.YES)
    String field4 = "field4";

    @ToString
    double field5 = 52.52;
}
```
- Демонстрирует различные комбинации аннотаций:
    - Аннотация на классе без указания mode (по умолчанию YES)
    - Поля без аннотаций (наследуют поведение от класса)
    - Явное указание Mode.NO для поля
    - Явное указание Mode.YES для поля
    - Аннотация без указания mode для поля

**ClassToStringNoMode**
```java
@ToString(Mode.NO)
class ClassToStringNoMode {
    String field1 = "field1";
    int field2 = 42;
}
```
- Демонстрирует поведение при глобальном отключении toString для класса


![Img](https://github.com/MineMoon/JavaLab6/blob/main/PNGFR/ProcessToStringTest1.png)

## Интеграционное тестирование

### Класс Person как комплексный пример

**Person** служит интеграционным тестом, содержа все аннотации одновременно:
```java
@Default(String.class)
@ToString(Mode.YES)
@Validate({Integer.class, String.class, Double.class, Boolean.class})
@Two(first = "Legend", second = 19)
@Cache({"users", "orders", "products"})
public class Person {
    // Поля с различными аннотациями
    @ToString(Mode.NO)
    private String pastName;

    @Invoke
    public void greetings(){
        System.out.println(this.name + ": Hello!");
    }
}
```

## Результаты тестирования

Все тесты успешно проходят, подтверждая:
- Корректность работы Reflection API
- Правильность обработки аннотаций
- Адекватность обработки ошибок
- Соответствие поведения спецификациям аннотаций
- Устойчивость к граничным условиям

## Пакет main

### Класс Main

#### Назначение
Точка входа в программу, демонстрирующая работу всех реализованных классов.

## Пакет userinput
### Класс UserInput
Обеспечивает безопасный ввод данных с консоли:
- `inputInt()`, `inputPositiveInt()` - ввод чисел
- `inputDiaposonInt()` - ввод числа в диапазоне
- `inputChoiceInt()` - ввод выбора из меню
- `inputString()` - ввод строки


### Класс Check
Содержит методы валидации:
- `isInteger(String str)` - проверка целого числа
- `Positive(int num)` - проверка неотрицательности

## Пакет Mpair

### Класс Pair

#### Назначение
Обобщенный класс для хранения пары значений.

```java
public class Pair<T, U> {
    private final T first;
    private final U second;
    // конструкторы и методы
}
```

#### Особенности реализации:
- **Два обобщенных типа** - независимые типы для первого и второго элемента
- **Неизменяемость** - поля объявлены как `final`
- **Переопределенные методы** - `equals()`, `hashCode()`, `toString()`
