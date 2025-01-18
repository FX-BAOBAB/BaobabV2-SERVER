FROM openjdk:17-jdk-slim
ADD build/libs/*.jar file.jar
ENTRYPOINT ["java","-jar","/file.jar"]