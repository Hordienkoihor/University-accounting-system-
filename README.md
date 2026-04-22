# Запуск проєкту
## Передумови

Встановлено JDK 17 або новіший
Встановлено Maven або Gradle

### Перевірити версію Java:
`java -version`

### Клонування репозиторію
`git clone https://github.com/Hordienkoihor/University-accounting-system-` <br>
`cd <repo-name>`

### Збірка та запуск (Maven)
`mvn clean package` <br>
`java -jar target/universityProjectASD-1.0-SNAPSHOT.jar` <br>

### Збірка та запуск (Gradle)
`./gradlew build ` <br>
`java -jar build/libs/<назва-артефакту>.jar`