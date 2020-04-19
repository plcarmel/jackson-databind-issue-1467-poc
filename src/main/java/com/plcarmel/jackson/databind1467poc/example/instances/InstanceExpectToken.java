package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.io.IOException;
import java.util.List;

public class InstanceExpectToken extends InstanceNoData {

  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;

  private boolean isDone = false;

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    List<DeserializationStepInstance<?>> dependencies
  ) {
    super(dependencies);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    useTokenValue = true;
  }

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    List<DeserializationStepInstance<?>> dependencies
  ) {
    super(dependencies);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    useTokenValue = false;
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    return parser.currentToken() == expectedTokenKind &&
      (!useTokenValue || parser.getCurrentValue().equals(expectedTokenValue));
  }

  @Override
  public void pushToken(JsonParser parser) throws IOException {
    if (parser.currentToken() != expectedTokenKind)
      throw new JsonParseException(parser, "Was expecting token " + parser.currentToken());
    parser.nextToken();
    isDone = true;
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public boolean isDone() {
    return isDone;
  }

  @Override
  public List<DeserializationStepInstance<?>> getDependencies() {
    return null;
  }

}
