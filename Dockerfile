FROM openjdk:20

WORKDIR /app

COPY target/delivery-app-3.2.0.jar delivery-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.language=pt", "-Duser.country=BR", "-jar", "delivery-app.jar"]