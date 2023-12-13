FROM amazoncorretto:17
COPY target/*.jar productcatelog-0.0.1.jar
EXPOSE 8090
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "productcatelog-0.0.1.jar"]
