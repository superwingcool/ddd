FROM openjdk:8-jre

COPY target/dev-cloud-code-check-summary-0.0.1.BUILD-SNAPSHOT.jar /var/code-check/

WORKDIR /var/code-check/

CMD sleep 30 && java -Dspring.profiles.active=integration-test -jar dev-cloud-code-check-summary-0.0.1.BUILD-SNAPSHOT.jar


