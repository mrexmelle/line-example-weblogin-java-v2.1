
FROM openjdk:9-jre-slim
MAINTAINER Matthew Tanudjaja <mrexmelle@gmail.com>
WORKDIR /
COPY build/libs/line-weblogin-example-0.2.1.jar line-weblogin-example-0.2.1.jar
EXPOSE 8080
CMD java -jar line-weblogin-example-0.2.1.jar
