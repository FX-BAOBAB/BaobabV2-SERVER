FROM openjdk:17-jdk-slim
ADD /article/build/libs/*.jar article.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/article.jar"]