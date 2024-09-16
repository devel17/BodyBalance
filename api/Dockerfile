FROM gradle:7.6.4-jdk17-jammy
WORKDIR /app
COPY . /app
ENTRYPOINT ["java", "-jar", "./build/libs/api.jar"]
