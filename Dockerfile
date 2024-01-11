# MAVEN BULD

FROM maven:3.8.7-eclipse-temurin-17-alpine AS builder

RUN mkdir app

WORKDIR /app

COPY src ./src/

COPY pom.xml ./

RUN mvn clean package -DskipTests

# JAVA 17 RUNTIME

FROM eclipse-temurin:17-jre

RUN mkdir app

WORKDIR /app

COPY --from=builder /app/target/hobby_garden_server-0.0.1-SNAPSHOT.jar ./hobby_garden_server-latest.jar

COPY wait-for-it.sh .

EXPOSE 8080

ENTRYPOINT [ "./wait-for-it.sh", "neo4j:7687", "--" ]

CMD [ "java", "-jar", "hobby_garden_server-latest.jar" ]
