package com.plcarmel.jackson.databind1467poc.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.generic.configuration.CachedTypeConfigurationFactory;
import com.plcarmel.jackson.databind1467poc.jackson.builders.SimpleStepFactory;
import com.plcarmel.jackson.databind1467poc.theory.StepBuilder;
import com.plcarmel.jackson.databind1467poc.theory.Interpreter;

import java.io.IOException;

public class ObjectMapper {

  public <T> T readValue(String content, Class<T> valueType) throws IOException {
    final StepBuilder<JsonParser, ? extends T> builder =
      SimpleStepFactory
        .getInstance()
        .builderDeserializeBeanValue(CachedTypeConfigurationFactory.getInstance().getTypeConfiguration(valueType));
    final Interpreter<JsonParser, ? extends T> interpreter = new Interpreter<>(builder.build());
    final JsonParser parser = JsonFactory.builder().build().createParser(content);
    parser.nextToken();
    while (parser.hasCurrentToken()) {
      interpreter.pushToken(parser);
    }
    interpreter.eof();
    if (!interpreter.isDone()) {
      throw new IOException("Not done.");
    }
    return interpreter.getData();
  }

}
