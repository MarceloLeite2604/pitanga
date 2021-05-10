package com.github.marceloleite2604.pitanga.model.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marceloleite2604.pitanga.model.OutgoingEvent;

import java.io.IOException;
import java.util.Objects;

public class OutgoingEventSerializer extends StdSerializer<OutgoingEvent<?>> {

    public OutgoingEventSerializer() {
        this(null);
    }

    protected OutgoingEventSerializer(Class<OutgoingEvent<?>> t) {
        super(t);
    }

    @Override
    public void serialize(OutgoingEvent<?> outgoingEvent, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", outgoingEvent.getType()
                .getValue());
        writePayload(outgoingEvent, jsonGenerator);
        jsonGenerator.writeEndObject();
    }

    private void writePayload(OutgoingEvent<?> outgoingEvent, JsonGenerator jsonGenerator) throws IOException {
        if (Objects.isNull(outgoingEvent.getType()
                .getPayloadClass())) {
            return;
        }
        if (Objects.isNull(outgoingEvent.getPayload())) {
            return;
        }

        var objectMapper = (ObjectMapper) jsonGenerator.getCodec();
        jsonGenerator.writeFieldName("payload");
        String payloadValue = objectMapper.writerFor(outgoingEvent.getType()
                .getPayloadClass())
                .writeValueAsString(outgoingEvent.getPayload());
        jsonGenerator.writeRawValue(payloadValue);
    }
}
