package com.iotlambda.generator.publishing;

import java.util.List;

public interface DataPublisher<T> {
    void publish(List<T> data);
}
