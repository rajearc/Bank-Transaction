FROM adoptopenjdk/openjdk8
ENV APP_HOME=/Users/archana/Desktop
WORKDIR $APP_HOME
COPY /target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]