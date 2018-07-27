package com.iotlambda.generator.generation;

import com.iotlambda.generator.domain.Device;
import com.iotlambda.generator.domain.DeviceTelemetry;
import com.iotlambda.generator.domain.telemetry.AirPressure;
import com.iotlambda.generator.domain.telemetry.Telemetry;
import com.iotlambda.generator.domain.telemetry.Temperature;
import com.iotlambda.generator.publishing.DataPublisher;
import com.iotlambda.generator.serialization.JsonSerializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.iotlambda.generator.domain.DeviceType.AIR_PRESSURE_SENSOR;
import static com.iotlambda.generator.domain.DeviceType.TEMPERATURE_SENSOR;

public final class DeviceTelemetryGenerator implements DataGenerator<DeviceTelemetry> {

    private static final double MIN_DOUBLE = 1;
    private static final double MAX_DOUBLE = 1000;

    private JsonSerializer serializer;
    private DataPublisher<String> publisher;
    private int timeoutMs;

    public DeviceTelemetryGenerator(
            JsonSerializer serializer,
            DataPublisher<String> publisher,
            int timeoutMs) {
        this.serializer = serializer;
        this.publisher = publisher;
        this.timeoutMs = timeoutMs;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public List<DeviceTelemetry> start() throws InterruptedException {
        while (true) {
            DeviceTelemetry telemetry = generateTelemetry(ThreadLocalRandom.current().nextInt());
            String json = serializer.serialize(telemetry);
            publisher.publish(Collections.singletonList(json));
            Thread.sleep(timeoutMs);
        }
    }

    private DeviceTelemetry generateTelemetry(int i) {
        boolean isEven = i % 2 == 0;
        if (isEven) {
            Device device = new Device("Test air pressure sensor", AIR_PRESSURE_SENSOR, "123452");
            Telemetry telemetry = new AirPressure(ThreadLocalRandom.current().nextDouble(MIN_DOUBLE, MAX_DOUBLE));

            return new DeviceTelemetry(device, telemetry, DateTime.now(DateTimeZone.UTC));
        } else {
            Device device = new Device("Test temperature sensor", TEMPERATURE_SENSOR, "7563455");
            Telemetry telemetry = new Temperature(ThreadLocalRandom.current().nextDouble(MIN_DOUBLE, MAX_DOUBLE));

            return new DeviceTelemetry(device, telemetry, DateTime.now(DateTimeZone.UTC));
        }
    }
}
