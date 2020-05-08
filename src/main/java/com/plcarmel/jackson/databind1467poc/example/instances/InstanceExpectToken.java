package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public final class InstanceExpectToken extends InstanceHavingUnmanagedDependencies<NoData>
  implements GetDependenciesMixin<DeserializationStepInstance<?>>, NoDataMixin
{
  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;

  private boolean tokenReceived = false;

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    InstanceGroupMany unmanaged
  ) {
    super(unmanaged);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    useTokenValue = true;
  }

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    InstanceGroupMany unmanaged
  ) {
    super(unmanaged);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    useTokenValue = false;
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    try {
      return parser.currentToken() == expectedTokenKind &&
        (!useTokenValue || parser.getCurrentName().equals(expectedTokenValue));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void pushToken(JsonParser parser) throws IOException {
    if (parser.currentToken() != expectedTokenKind)
      throw new JsonParseException(parser, "Was expecting token " + parser.currentToken());
    parser.nextToken();
    tokenReceived = true;
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public boolean areDependenciesSatisfied() {
    return unmanaged == null || unmanaged.areDependenciesSatisfied();
  }

  @Override
  public boolean isDone() {
    return tokenReceived && areDependenciesSatisfied();
  }

  @Override
  public DependencyGroups<DeserializationStepInstance<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }

  @Override
  public List<DeserializationStepInstance<?>> getDependencies() {
    return emptyList();
  }
}
