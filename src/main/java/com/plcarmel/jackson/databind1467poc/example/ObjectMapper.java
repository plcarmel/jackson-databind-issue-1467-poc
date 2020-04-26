package com.plcarmel.jackson.databind1467poc.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.example.configuration.CachedTypeConfigurationFactory;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepBuilder;
import com.plcarmel.jackson.databind1467poc.theory.Interpreter;

import java.io.IOException;
import java.util.HashMap;

public class ObjectMapper {

  public <T> T readValue(String content, Class<T> valueType) throws IOException {
    final DeserializationStepBuilder<T> builder =
      SimpleDeserializationStepFactory
        .getInstance()
        .builderDeserializeValue(CachedTypeConfigurationFactory.getInstance().getTypeConfiguration(valueType));
    final Interpreter<T> interpreter = new Interpreter<>(builder.build().instantiated(new HashMap<>()));
    final JsonParser parser = JsonFactory.builder().build().createParser(content);
    parser.nextToken();
    while (parser.hasCurrentToken()) {
      interpreter.pushToken(parser);
    }
    if (!interpreter.isDone()) {
      throw new IOException("Not done.");
    }
    return interpreter.getData();
  }

}
