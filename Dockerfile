FROM amazoncorretto:17
COPY target/*.jar productcatelog-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "productcatelog-0.0.1-SNAPSHOT.jar"]
