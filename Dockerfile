FROM openjdk:10.0.1-jdk-slim as buildContainer
RUN apt-get update && apt-get install -y curl
WORKDIR /app

# Copy files necessary to run gradle wrapper
COPY ./gradle ./gradle
COPY ./gradlew ./
# Trigger download of gradle distribution
RUN ./gradlew --version

# Copy build.gradle files
COPY ./*.gradle ./
# Trigger builds of subprojects
RUN ./gradlew getDependencies

# Copy the rest of the source code
COPY . ./
ENV APP_NAME=docker-security
RUN mkdir build-output && \
    ./gradlew build -x test && \
    tar xvf build/distributions/${APP_NAME}.tar -C build-output
######################################################

FROM openjdk:10.0.1-jre-slim
ENV APP_NAME=docker-security

COPY --from=buildContainer /app/build-output/${APP_NAME}/. /app
WORKDIR /app/bin

EXPOSE 8080

ENTRYPOINT ["./docker-security"]
