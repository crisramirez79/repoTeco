FROM openjdk:8-jdk-alpine
ADD target/AWS_Workshop-0.0.1-SNAPSHOT.jar AWS_Workshop-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","AWS_Workshop-0.0.1-SNAPSHOT.jar"]