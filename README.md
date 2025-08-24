# util-lib

Набір маленьких утиліт для щоденних Java-задач: робота з датами, рядками, числами, JSON та простим CSV. Є демо-клас `Main` для швидкого запуску.

## Можливості

* `dates.DateUtils` — парсинг/форматування дат, різниця в днях, конвертація часових поясів, перевірка вихідних.
* `strings.StringValidator` — валідація e-mail, телефонів у форматі E.164, безпечний trim.
* `numbers.NumberConverters` — перетворення у `BigDecimal`, округлення, форматування валюти, безпечний `toInt`.
* `json.JsonUtils` — читання/запис JSON через Jackson, pretty-print.
* `csv.CsvUtils` — надпростий CSV парсер/генератор (без лапок і ком у полях).
* `Main` — приклад використання.

## Стек і вимоги

* Java 17+ (рекомендовано LTS).
* Для `JsonUtils` потрібен Jackson:

  * `com.fasterxml.jackson.core:jackson-databind`
  * бажано `jackson-datatype-jsr310` (реєструється автоматично через `findAndRegisterModules()`).
* Мова і приклади — українською.

## Структура проєкту

```
util-lib/
└─ src/main/java/com/example/
   ├─ Main.java
   └─ util/
      ├─ dates/DateUtils.java
      ├─ strings/StringValidator.java
      ├─ numbers/NumberConverters.java
      ├─ json/JsonUtils.java
      └─ csv/CsvUtils.java
```

## Швидкий старт

### Варіант A. Запуск без збірника

```bash
# У корені проєкту
javac -d out $(git ls-files '*.java')
java -cp out com.example.Main
```

Очікуваний вивід:
`Сьогодні вихідний? true|false` залежно від поточного дня.

### Варіант B. Maven (рекомендовано)

1. Створіть `pom.xml` у корені:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>util-lib</artifactId>
  <version>0.1.0</version>
  <properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
  </properties>
  <dependencies>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.17.2</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.datatype</groupId>
      <artifactId>jackson-datatype-jsr310</artifactId>
      <version>2.17.2</version>
    </dependency>
  </dependencies>
</project>
```

2. Збірка та запуск:

```bash
mvn -q -e -DskipTests package
mvn -q exec:java -Dexec.mainClass="com.example.Main" \
  -Dexec.cleanupDaemonThreads=false
```

> Якщо немає `exec-maven-plugin`, додайте його або запустіть `java -cp target/classes:~/.m2/repository/... com.example.Main`.

### Варіант C. Gradle

`build.gradle` (Groovy):

```groovy
plugins { id 'java' }
group = 'com.example'
version = '0.1.0'
java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

repositories { mavenCentral() }
dependencies {
  implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.2'
  implementation 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2'
}

tasks.register('runMain', JavaExec) {
  classpath = sourceSets.main.runtimeClasspath
  mainClass = 'com.example.Main'
}
```

Запуск:

```bash
gradle runMain
```

## Використання: короткі приклади

### DateUtils

```java
LocalDate d = DateUtils.parseIsoDate("2025-08-24");
String uk = DateUtils.format(d, "d MMMM yyyy", new Locale("uk"));
long diff = DateUtils.daysBetween(LocalDate.now(), d);
LocalDateTime kyiv = DateUtils.toKyiv(LocalDateTime.now(), ZoneId.of("UTC"));
boolean isWknd = DateUtils.isWeekend(LocalDate.now());
```

### StringValidator

```java
StringValidator.notBlank("  hi ");      // true
StringValidator.isEmail("a@b.com");     // true
StringValidator.isPhoneE164("+380501112233"); // true
StringValidator.safeTrim(null);         // ""
```

> Примітка: регулярка E.164 вимагає `+` і 8–15 цифр. Формати з пробілами/дефісами не пройдуть.

### NumberConverters

```java
BigDecimal x = NumberConverters.toDecimal(" 123.456 ");
BigDecimal r = NumberConverters.round(x, 2);            // 123.46
String uah = NumberConverters.formatCurrency(r, new Locale("uk", "UA")); // ₴123.46
int n = NumberConverters.toIntSafe("oops", 42);         // 42
```

### JsonUtils

```java
JsonNode node = JsonUtils.parse("{\"a\":1}");
String pretty = JsonUtils.toPretty(node);
MyDto dto = JsonUtils.fromJson("{\"name\":\"Ada\"}", MyDto.class);
String json = JsonUtils.toJson(Map.of("x", 1));
```

> У разі невалідного JSON кидає `IllegalArgumentException`.

### CsvUtils

```java
List<String[]> rows = CsvUtils.parseSimple("a,b,c\n1,2,3");
String csv = CsvUtils.toCsv(List.of(new String[]{"x","y"})); // "x,y"
```

> Обмеження: немає екранування лапок, роздільник завжди кома, коми всередині поля не підтримуються.

## Підключення як бібліотеки в іншому проєкті

### Локальне встановлення в Maven-репозиторій

```bash
mvn -q -DskipTests install
```

Далі у вашому іншому `pom.xml`:

```xml
<dependency>
  <groupId>com.example</groupId>
  <artifactId>util-lib</artifactId>
  <version>0.1.0</version>
</dependency>
```

У Gradle:

```groovy
implementation 'com.example:util-lib:0.1.0'
```

## Нюанси та поради

* Часовий пояс: `toKyiv` конвертує у `Europe/Kyiv` з урахуванням DST.
* Локалі: для форматів дат і валюти передавайте коректну `Locale` (наприклад, `new Locale("uk", "UA")`).
* Помилки перетворення чисел: `toDecimal` кине `NumberFormatException`, якщо рядок некоректний.
* Потокобезпека: усі методи статичні й безстанні; `ObjectMapper` — єдиний інстанс, безпечний для багатопоточного читання/запису конфігурації, тут не змінюється після ініціалізації.
* Продуктивність: для великих CSV використовуйте спеціалізовані бібліотеки (OpenCSV, Univocity).

## Тести (необов’язково)

Рекомендовано додати JUnit 5:

```xml
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.10.3</version>
  <scope>test</scope>
</dependency>
```

Та базові кейси на:

* `isWeekend` для всіх днів тижня.
* `isEmail` і `isPhoneE164` з валідними/невалідними прикладами.
* `round` для негативних скейлів і великих чисел.
* JSON parse з датами JavaTime.


