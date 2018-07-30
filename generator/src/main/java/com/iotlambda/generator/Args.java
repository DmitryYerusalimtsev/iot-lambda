package com.iotlambda.generator;

import com.beust.jcommander.Parameter;

class Args {
    @Parameter(names = "-kafkaServer", description = "Kafka host. Format: host:port (localhost:9092)")
    String kafkaServer = "localhost:9092";

    @Parameter(names = "-kafkaTopic", description = "Topic n Kafka")
    String kafkaTopic = "iot-telemetry";

    @Parameter(names = "-timeoutMs", description = "Generation timeout")
    int timeoutMs = 500;
}
