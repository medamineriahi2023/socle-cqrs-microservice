package com.oga.ged.command.rest.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.Binary;

import java.io.IOException;

public class BinaryDeserializer extends JsonDeserializer<Binary> {
    @Override
    public Binary deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        byte[] data = p.getBinaryValue();
        return new Binary(data);
    }
}
