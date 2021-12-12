FROM amazoncorretto:11.0.13
LABEL maintainer="camper@gmail.com"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# 실행 명령
ENTRYPOINT ["java","-jar","/app.jar"]

### MYSQL
#FROM mysql:5.7
#MAINTAINER camper <vlll3320@gmail.com>
#EXPOSE 3306
#CMD ["mysqld"]