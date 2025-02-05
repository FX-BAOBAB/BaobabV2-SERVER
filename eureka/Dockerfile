FROM openjdk:21-jdk-slim
ADD /build/libs/*.jar eureka.jar
ENTRYPOINT ["java", "-jar", "/eureka.jar"]