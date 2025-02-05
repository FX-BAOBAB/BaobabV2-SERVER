FROM openjdk:21-jdk-slim
ADD /build/libs/*.jar gateway.jar
ENTRYPOINT ["java", "-jar", "/gateway.jar"]