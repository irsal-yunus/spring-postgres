FROM adoptopenjdk/openjdk11:alpine-jre
ADD build/libs/spring-postgres.jar spring-postgres.jar
ENTRYPOINT ["java","-jar","spring-postgres.jar"]