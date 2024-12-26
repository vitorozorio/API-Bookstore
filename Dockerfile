# Etapa de Build - Usando Maven com Eclipse Temurin 17
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo pom.xml e o diretório src
COPY pom.xml .
COPY src ./src

# Rodar o comando Maven para construir o JAR (sem rodar testes)
RUN mvn clean package -DskipTests

# Etapa de Execução - Usando OpenJDK 17 para rodar o JAR
FROM eclipse-temurin:17-jdk-focal

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR gerado pela etapa de build
COPY --from=build /app/target/Spring_Bookstore-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta onde o Spring Boot vai rodar
EXPOSE 8080

# Definir o comando para rodar a aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
