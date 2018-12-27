
FROM openjdk:9-jre-slim
LABEL maintainer="Matthew Tanudjaja <mrexmelle@gmail.com>"
WORKDIR /
COPY build/libs/line-weblogin-example-2.1.0.jar line-weblogin-example-2.1.0.jar
EXPOSE 8080
CMD java -jar line-weblogin-example-2.1.0.jar
