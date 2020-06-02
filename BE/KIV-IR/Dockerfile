### STAGE 1: BUILD ###
FROM maven:3.6.3-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package

### STAGE 2: RUN ###
FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /workspace/target/*.jar app.jar
COPY ./stopwords.txt stopwords.txt
COPY ./articles.json articles.json
COPY ./TREC/czechData.bin ./TREC/czechData.bin
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
