FROM openjdk:21-jdk-slim
ADD /build/libs/*.jar chat.jar
ENTRYPOINT ["java", "-jar", "/chat.jar"]