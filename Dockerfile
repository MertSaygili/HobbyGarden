FROM eclipse-temurin:17-jre

WORKDIR /app

COPY ./target/hobby_garden-latest.jar .

COPY ./wait-for-it.sh .

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "neo4j:7687", "--"]

CMD ["java", "-jar", "hobby_garden-latest.jar"]
