FROM openjdk:11
LABEL authors="Team: Hassan - Ahmed - Magdy - Alseddek"
WORKDIR /app
COPY . .
COPY target/javeats.jar javeats.jar
ENTRYPOINT ["java", "-jar","javeats.jar"]