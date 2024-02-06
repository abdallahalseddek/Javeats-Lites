FROM openjdk:11
LABEL authors="alseddek"
WORKDIR /app
COPY . .
COPY target/javeats.jar javeats.jar
ENTRYPOINT ["java", "-jar","javeats.jar"]