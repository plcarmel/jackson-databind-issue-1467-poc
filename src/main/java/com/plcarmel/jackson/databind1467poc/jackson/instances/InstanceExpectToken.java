package com.plcarmel.jackson.databind1467poc.jackson.instances;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.generic.groups.*;
import com.plcarmel.jackson.databind1467poc.generic.instances.InstanceHavingUnmanagedDependencies;
import com.plcarmel.jackson.databind1467poc.generic.instances.NoDataMixin;
import com.plcarmel.jackson.databind1467poc.generic.instances.NonExecutableMixin;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.NoData;

import java.io.IOException;
import java.util.stream.Stream;

public final class InstanceExpectToken
  extends
    InstanceHavingUnmanagedDependencies<JsonParser, NoData>
  implements
    GetDependenciesMixin<InstanceGroup<JsonParser>, StepInstance<JsonParser, ?>>,
    RemoveDependencyFromListMixin<JsonParser, NoData>,
    CollapseMixin<JsonParser, NoData>,
    NoDataMixin<JsonParser>,
    NonExecutableMixin<JsonParser, NoData>
{
  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;
  private final boolean isOptional;

  private boolean tokenReceived = false;

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    boolean isOptional,
    InstanceGroupMany<JsonParser> unmanaged
  ) {
    super(unmanaged);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    this.isOptional = isOptional;
    useTokenValue = true;
  }

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    boolean isOptional,
    InstanceGroupMany<JsonParser> unmanaged
  ) {
    super(unmanaged);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = null;
    this.isOptional = isOptional;
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
    return isOptional;
  }

  @Override
  public boolean hasTokenBeenReceived() {
    return tokenReceived;
  }

  @Override
  public InstanceDependencyGroups<JsonParser> getDependencyGroups() {
    return new InstanceDependencyGroups<>(Stream.of(unmanaged));
  }

}
