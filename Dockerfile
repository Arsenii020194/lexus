FROM maven:3.8.1-jdk-11
WORKDIR /lexus
ADD  ./ ./lexus/
EXPOSE 8080
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD

CMD cd lexus/ && mvn -P metadata com.jaxio.celerio:dbmetadata-maven-plugin:extract-metadata -Djdbc.url=${SPRING_DATASOURCE_URL} -Djdbc.user=${SPRING_DATASOURCE_USERNAME} -Djdbc.password=${SPRING_DATASOURCE_PASSWORD} && mvn package -P gen -Djdbc.url=${SPRING_DATASOURCE_URL} -Djdbc.user=${SPRING_DATASOURCE_USERNAME} -Djdbc.password=${SPRING_DATASOURCE_PASSWORD} && java -jar target/lexus-0.0.1-SNAPSHOT.jar