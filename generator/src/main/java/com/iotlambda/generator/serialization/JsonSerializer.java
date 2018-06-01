package com.iotlambda.generator.serialization;

public interface JsonSerializer {
    <T> String serialize(T obj);
}
