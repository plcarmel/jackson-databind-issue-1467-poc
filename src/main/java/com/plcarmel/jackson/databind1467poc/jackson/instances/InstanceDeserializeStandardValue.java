package com.plcarmel.jackson.databind1467poc.jackson.instances;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceDependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroup;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.mixins.CleanMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.mixins.RemoveDependencyFromListMixin;
import com.plcarmel.jackson.databind1467poc.generic.instances.mixins.NonExecutableMixin;
import com.plcarmel.jackson.databind1467poc.jackson.SupportedTypes;
import com.plcarmel.jackson.databind1467poc.generic.instances.bases.InstanceUnmanagedBase;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static com.fasterxml.jackson.core.JsonToken.VALUE_TRUE;

public final class InstanceDeserializeStandardValue<TResult>
  extends
  InstanceUnmanagedBase<JsonParser, TResult>
  implements
  GetDependenciesMixin<InstanceGroup<JsonParser>, StepInstance<JsonParser, ?>>,
  RemoveDependencyFromListMixin<JsonParser, TResult>,
  CleanMixin<JsonParser, TResult>,
    NonExecutableMixin<JsonParser, TResult>
{
  private final PropertyConfiguration<?, TResult> conf;
  private TResult data;
  private boolean isPropertyDeserialized = false;

  public InstanceDeserializeStandardValue(
    PropertyConfiguration<?, TResult> conf,
    InstanceGroupMany<JsonParser> unmanaged
  ) {
    super(unmanaged);
    this.conf = conf;
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    return getDependencies().isEmpty() && parser.getCurrentToken().isScalarValue();
  }

  @Override
  public void pushToken(JsonParser parser) throws IOException {
    // Of course, this would be replaced with the current implementation of jackson-databind, which is much
    // more sophisticated than this. We are not supporting dates, among other things.
    final JsonParseException notTheAppropriateType = new JsonParseException(parser, "Not the appropriate type.");
    final JsonToken currentToken = parser.getCurrentToken();
    final Class<TResult> typeClass = conf.getTypeConfiguration().getType();
    switch (currentToken) {
      case NOT_AVAILABLE:
        break;
      case VALUE_NULL:
        if (conf.isRequired()) throw new JsonParseException(parser, "Value is required");
        data = null;
        isPropertyDeserialized = true;
        parser.nextToken();
        break;
      case VALUE_FALSE:
      case VALUE_TRUE:
        if (typeClass != Boolean.class) throw notTheAppropriateType;
        //noinspection unchecked
        data = (TResult) (currentToken == VALUE_TRUE ? Boolean.TRUE : Boolean.FALSE);
        break;
      case VALUE_NUMBER_FLOAT:
      case VALUE_NUMBER_INT:
      case VALUE_STRING:
        final Set<Class<?>> classes = SupportedTypes.tokenToSupportedTypes.get(currentToken);
        if (classes == null) throw notTheAppropriateType;
        //noinspection unchecked
        data = (TResult) SupportedTypes.typeToValueParser.get(typeClass).apply(parser);
        isPropertyDeserialized = true;
        parser.nextToken();
        break;
      default:
        throw new JsonParseException(parser, "Unexpected token " + parser.getCurrentToken());
    }
  }

  @Override
  public boolean isOptional() {
    return !conf.isRequired();
  }

  @Override
  public boolean hasTokenBeenReceived() {
    return isPropertyDeserialized;
  }

  @Override
  public TResult getData() {
    return data;
  }

  @Override
  public InstanceDependencyGroups<JsonParser> getDependencyGroups() {
    return new InstanceDependencyGroups<>(Stream.of(unmanaged));
  }
}
