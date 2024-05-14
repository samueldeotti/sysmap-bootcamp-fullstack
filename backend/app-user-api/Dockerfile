FROM eclipse-temurin:17-jdk-jammy as build-image
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests=true


FROM eclipse-temurin:17-jre-jammy
COPY --from=build-image /app/target/*.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]