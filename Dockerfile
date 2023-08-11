#
# Build stage
#
FROM maven:3.8.1 AS build
COPY . .

#
# Package stage
#
FROM openjdk
COPY --from=build /target/YLSWebsite-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]