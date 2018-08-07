CREATE TABLE IF NOT EXISTS iot_lambda_speed.avg_telemetry_by_device_type (
    "timestamp" timestamp,
    deviceType text,
    avgValue double,
    PRIMARY KEY (timestamp)
    );