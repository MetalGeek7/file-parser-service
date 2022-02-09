FROM maven:3.6.3-openjdk-11-slim as BUILDER
ARG VERSION=0.0.1
ENV PATH="$PATH:/bin/"
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/

RUN mvn clean package
COPY target/file-processor-${VERSION}-SNAPSHOT.jar target/file-processor.jar

FROM openjdk:8-jdk-alpine
WORKDIR /app/
# This path must exist as it is used as a mount point for testing
# Ensure your app is loading files from this location
RUN mkdir /app/test-files

COPY --from=BUILDER /build/target/file-processor.jar /app/
CMD ["java","-jar","/app/file-processor.jar"]