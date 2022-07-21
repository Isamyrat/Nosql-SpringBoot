
FROM openjdk:17
VOLUME /tmp
ADD target/nosql.jar app.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo:27017/test_one", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
