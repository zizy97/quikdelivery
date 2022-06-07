FROM openjdk:11
ADD target/quikapp-backend.jar quikapp-backend.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar", "quikapp-backend.jar"]
