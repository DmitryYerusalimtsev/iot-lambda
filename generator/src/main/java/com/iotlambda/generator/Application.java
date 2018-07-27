package com.iotlambda.generator;

import com.beust.jcommander.JCommander;
import com.iotlambda.generator.domain.DeviceTelemetry;
import com.iotlambda.generator.generation.DataGenerator;
import com.iotlambda.generator.generation.DeviceTelemetryGenerator;
import com.iotlambda.generator.publishing.DataPublisher;
import com.iotlambda.generator.publishing.KafkaDataPublisher;
import com.iotlambda.generator.serialization.GsonSerializer;
import com.iotlambda.generator.serialization.JsonSerializer;

public class Application {
    public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        System.out.println(String.format("Kafka server: %s", arguments.kafkaServer));
        System.out.println(String.format("Kafka topic: %s", arguments.kafkaTopic));

        run(arguments);
    }

    private static void run(Args args) {
        JsonSerializer serializer = new GsonSerializer();
        DataPublisher<String> publisher = new KafkaDataPublisher<>(args.kafkaServer, args.kafkaTopic);

        DataGenerator<DeviceTelemetry> generator = new DeviceTelemetryGenerator(serializer, publisher, 500);

        try {
            generator.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
