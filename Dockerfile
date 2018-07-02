FROM openjdk:8-jdk-alpine as build
RUN apk add --no-cache curl
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

FROM openjdk:8-jre-alpine
ENV APP_NAME=docker-security

COPY --from=build /app/build-output/${APP_NAME}/. /app
WORKDIR /app/bin

EXPOSE 8080

ENTRYPOINT ["./docker-security"]
