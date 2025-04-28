FROM openjdk:21-jdk-slim
ADD /build/libs/*.jar message.jar
ENTRYPOINT ["java", "-jar", "/message.jar"]