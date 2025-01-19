FROM openjdk:17-jdk-slim
ADD build/libs/*.jar article.jar
ENTRYPOINT ["java", "-jar", "/article.jar"]