FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml install
RUN mvn -f /home/app/pom.xml clean package
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY --from=build /home/app/target/spring-boot-cart-service-0.0.1-SNAPSHOT.jar /usr/local/lib/cart.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/cart.jar"]