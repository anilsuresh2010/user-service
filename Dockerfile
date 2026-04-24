# Production stage
FROM eclipse-temurin:17-jre-alpine

# Install curl for health checks (Alpine uses apk)
RUN apk add --no-cache curl

# Create a non-root user
RUN addgroup -S appuser && adduser -S appuser -G appuser

WORKDIR /app

# Copy the JAR from builder stage
COPY --from=builder /app/target/user-service-*.jar app.jar

RUN chown -R appuser:appuser /app
USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

LABEL maintainer="DevOps Team"
LABEL version="1.0"
LABEL description="User Service Microservice"
LABEL org.opencontainers.image.source="https://github.com/your-org/ecommerce-microservices-platform"
