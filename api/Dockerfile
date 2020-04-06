FROM java:8-jdk-alpine

WORKDIR /usr/app

COPY ./target/help-desk-mobile-api.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=dev", "help-desk-mobile-api.jar"]