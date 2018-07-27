FROM openjdk

MAINTAINER Dmytro Yerusalimtsev

COPY target/iot-generator-1.0.jar /etc/

ENV KAFKA_SERVER=""
ENV KAFKA_TOPIC=""
ENV TIMEOUT_MS=500

CMD java -jar "/etc/iot-generator-1.0.jar" -kafkaServer $KAFKA_SERVER -kafkaTopic $KAFKA_TOPIC -timeoutMs $TIMEOUT_MS