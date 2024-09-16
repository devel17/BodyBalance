FROM gradle:7.6.4-jdk17-jammy
#FROM zenika/kotlin
# ENV ASPNETCORE_ENVIRONMENT=Development
# ENV JDK_ENVIRONMENT=Development
WORKDIR /app
COPY . /app
RUN ls -alh .
# ENV KOTLIN_VERSION 1.9.21
# RUN wget "https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip" &&\
#     unzip kotlin-compiler-${KOTLIN_VERSION}.zip -d / && mv /kotlinc /usr/lib/
# RUN gradle build -x test
ENTRYPOINT ["java", "-jar", "./build/libs/api.jar"]
