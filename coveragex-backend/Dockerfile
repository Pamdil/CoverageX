# =========================
# Build stage (Maven + JDK 17)
# =========================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# =========================
# Runtime stage (JRE 17)
# =========================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

ENV APP_PORT=8000 JAVA_OPTS=""
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8000
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${APP_PORT} -jar /app/app.jar"]
