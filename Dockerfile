FROM openjdk:17
COPY target/sopimart-0.0.1-SNAPSHOT.jar /sopimart-app.jar
ENTRYPOINT ["java", "-jar", "/sopimart-app.jar"]