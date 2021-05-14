package com.github.marceloleite2604.pitanga.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingEvent;
import com.github.marceloleite2604.pitanga.model.incoming.IncomingEventType;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class IncomingEventDeserializer extends StdDeserializer<IncomingEvent<?>> {

    public IncomingEventDeserializer() {
        super(IncomingEvent.class);
    }

    @Override
    public IncomingEvent<?> deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        var rootJsonNode = jsonParser.getCodec()
                .<JsonNode>readTree(jsonParser);

        var incomingEventType = retrieveEventType(rootJsonNode);

        Optional<Object> optionalPayload = readPayload(rootJsonNode, context, incomingEventType);

        return IncomingEvent.builder()
                .type(incomingEventType)
                .payload(optionalPayload.orElse(null))
                .build();
    }

    private Optional<Object> readPayload(JsonNode rootJsonNode, DeserializationContext context, IncomingEventType incomingEventType) throws IOException {

        if (Objects.isNull(incomingEventType.getPayloadClass())) {
            return Optional.empty();
        }

        var payloadJsonNode = rootJsonNode.get("payload");
        if (Objects.isNull(payloadJsonNode)) {
            return Optional.empty();
        }

        return Optional.of(context.readValue(payloadJsonNode.traverse(), incomingEventType.getPayloadClass()));
    }

    private IncomingEventType retrieveEventType(JsonNode jsonNode) {
        return IncomingEventType.findByValue(jsonNode.get("type")
                .asText());
    }
}
