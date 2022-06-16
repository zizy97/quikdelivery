FROM openjdk:11
ADD target/quikapp-backend.jar quikapp-backend.jar
EXPOSE $PORT
ENTRYPOINT ["java","-jar", "quikapp-backend.jar", "-XX:+UseSerialGC", "-Xss512k", "-XX:MaxRAM=72m"]
