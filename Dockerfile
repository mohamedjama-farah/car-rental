# LOCATION: Dockerfile — project ROOT
#
# This file tells Docker how to build your app into a container.
#
# Stage 1: BUILD
#   Uses a Maven+Java image to compile and test the code.
#   Produces a JAR file in the target/ folder.
#
# Stage 2: RUN
#   Uses a smaller Java-only image to run the JAR.
#   We do not need Maven at runtime — only Java.
#   This keeps the final image small.

# ── STAGE 1: build ────────────────────────────────────────────────────
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# copy pom.xml first — Docker caches this layer
# so Maven does not re-download dependencies every build
COPY pom.xml .
RUN mvn dependency:go-offline

# copy source code and build
COPY src ./src
RUN mvn package -DskipTests

# ── STAGE 2: run ──────────────────────────────────────────────────────
FROM eclipse-temurin:17-jre

WORKDIR /app

# copy only the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
