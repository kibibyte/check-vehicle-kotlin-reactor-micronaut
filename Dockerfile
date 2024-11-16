FROM eclipse-temurin:17-jre
WORKDIR /my-app
COPY app/build/libs/app-0.1-all.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]