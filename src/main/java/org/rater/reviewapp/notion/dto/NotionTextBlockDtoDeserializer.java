package org.rater.reviewapp.notion.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class NotionTextBlockDtoDeserializer extends StdDeserializer<NotionTextBlockDto> {

    public NotionTextBlockDtoDeserializer() {
        this(null);
    }

    protected NotionTextBlockDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public NotionTextBlockDto deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.path("id").asText();
        Boolean hasChildren = node.path("has_children").asBoolean();
        String type = node.path("type").asText();
        JsonNode data = node.get(type);
        return new NotionTextBlockDto(id, hasChildren, type, data);
    }
}
