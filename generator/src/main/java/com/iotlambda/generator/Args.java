package com.iotlambda.generator;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = "-kafkaServer", description = "Kafka host. Format: host:port (localhost:9092)")
    public String kafkaServer;

    @Parameter(names = "-kafkaTopic", description = "Topic n Kafka")
    public String kafkaTopic;
}
