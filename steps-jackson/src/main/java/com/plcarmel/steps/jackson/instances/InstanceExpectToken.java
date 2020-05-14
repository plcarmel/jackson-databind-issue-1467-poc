package com.plcarmel.steps.jackson.instances;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.steps.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.steps.generic.groups.instances.InstanceGroup;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.steps.generic.groups.instances.mixins.CleanMixin;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.instances.mixins.RemoveDependencyFromListMixin;
import com.plcarmel.steps.generic.instances.bases.InstanceUnmanagedBase;
import com.plcarmel.steps.generic.instances.mixins.NoDataMixin;
import com.plcarmel.steps.generic.instances.mixins.NonExecutableMixin;
import com.plcarmel.steps.theory.StepInstance;
import com.plcarmel.steps.theory.NoData;

import java.io.IOException;
import java.util.stream.Stream;

public final class InstanceExpectToken
  extends
    InstanceUnmanagedBase<JsonParser, NoData>
  implements
    GetDependenciesMixin<InstanceGroup<JsonParser>, StepInstance<JsonParser, ?>>,
    RemoveDependencyFromListMixin<JsonParser, NoData>,
    CleanMixin<JsonParser, NoData>,
    NoDataMixin<JsonParser>,
    NonExecutableMixin<JsonParser, NoData>
{
  private final JsonToken expectedTokenKind;
  private final Object expectedTokenValue;
  private final boolean useTokenValue;

  private boolean tokenReceived = false;

  public JsonToken getExpectedTokenKind() {
    return expectedTokenKind;
  }

  public Object getExpectedTokenValue() {
    return expectedTokenValue;
  }

  public boolean isUseTokenValue() {
    return useTokenValue;
  }

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    Object expectedTokenValue,
    InstanceGroupMany<JsonParser> unmanaged
  ) {
    super(unmanaged);
    this.expectedTokenKind = expectedTokenKind;
    this.expectedTokenValue = expectedTokenValue;
    useTokenValue = true;
  }

  public InstanceExpectToken(
    JsonToken expectedTokenKind,
    InstanceGroupMany<JsonParser> unmanaged
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
        (!useTokenValue || parser.getCurrentName().equals(expectedTokenValue)) &&
        getDependencies().isEmpty();
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
  public boolean isSubstitution() {
    return false;
  }

  @Override
  public boolean isTerminal() {
    return true;
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
