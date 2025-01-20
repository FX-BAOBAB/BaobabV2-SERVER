FROM openjdk:21-jdk-slim
ADD /build/libs/*.jar user.jar
ENTRYPOINT ["java","-jar","/user.jar"]