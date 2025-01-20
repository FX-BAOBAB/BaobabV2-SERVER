FROM openjdk:21-jdk-slim
ADD /article/build/libs/*.jar article.jar
ENTRYPOINT ["java", "-jar", "/article.jar"]