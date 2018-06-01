package com.iotlambda.generator.serialization;

import com.google.gson.Gson;

public class GsonSerializer implements JsonSerializer {
    public <T> String serialize(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
