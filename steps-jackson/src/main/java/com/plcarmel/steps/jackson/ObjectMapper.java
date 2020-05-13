package com.plcarmel.steps.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.jackson.configuration.CachedTypeConfigurationFactory;
import com.plcarmel.steps.jackson.builders.JacksonStepFactory;
import com.plcarmel.steps.theory.StepBuilder;
import com.plcarmel.steps.theory.Interpreter;
import com.plcarmel.steps.theory.StepInstance;

import java.io.IOException;
import java.util.function.Consumer;

public class ObjectMapper {

  public <T> T readValue(
    String content,
    Class<T> valueType,
    Consumer<StepInstance<JsonParser, ? extends T>> onNewGraph
  ) throws IOException {
    final StepBuilder<JsonParser, ? extends T> builder =
      JacksonStepFactory
        .getInstance()
        .builderDeserializeBeanValue(CachedTypeConfigurationFactory.getInstance().getTypeConfiguration(valueType));
    final Interpreter<JsonParser, ? extends T> interpreter = new Interpreter<>(builder.build());
    final JsonParser parser = JsonFactory.builder().build().createParser(content);
    parser.nextToken();
    while (parser.hasCurrentToken()) {
      if (onNewGraph != null) onNewGraph.accept(interpreter.getFinalStep());
      interpreter.pushToken(parser);
    }
    interpreter.eof();
    if (!interpreter.isDone()) {
      throw new IOException("Not done.");
    }
    return interpreter.getData();
  }

  public <T> T readValue(String content, Class<T> valueType) throws IOException {
    return readValue(content, valueType, null);
  }
}
