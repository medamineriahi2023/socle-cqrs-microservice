package com.oga.ged.command.rest.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.Binary;

import java.io.IOException;

public class BinarySerializer extends JsonSerializer<Binary> {
    @Override
    public void serialize(Binary value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        byte[] data = value.getData();
        gen.writeBinary(data);
    }
}