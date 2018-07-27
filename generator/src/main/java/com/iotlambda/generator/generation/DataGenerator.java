package com.iotlambda.generator.generation;

import java.util.List;

public interface DataGenerator<T> {
    List<T> start() throws InterruptedException;
}
