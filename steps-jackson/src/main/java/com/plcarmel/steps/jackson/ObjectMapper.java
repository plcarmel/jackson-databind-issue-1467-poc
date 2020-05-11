package com.plcarmel.steps.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.jackson.configuration.CachedTypeConfigurationFactory;
import com.plcarmel.steps.jackson.builders.JacksonStepFactory;
import com.plcarmel.steps.theory.StepBuilder;
import com.plcarmel.steps.theory.Interpreter;

import java.io.IOException;

public class ObjectMapper {

  public <T> T readValue(String content, Class<T> valueType) throws IOException {
    final StepBuilder<JsonParser, ? extends T> builder =
      JacksonStepFactory
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
