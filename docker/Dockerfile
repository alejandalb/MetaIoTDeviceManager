FROM eclipse-temurin:17-jdk-alpine
RUN apk add --no-cache bash
COPY target/*.jar metaIoT.jar
ENTRYPOINT ["java","-jar","/metaIoT.jar"]