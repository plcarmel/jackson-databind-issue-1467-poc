package com.plcarmel.jackson.databind1467poc.example.instances;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.plcarmel.jackson.databind1467poc.example.SupportedTypes;
import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.fasterxml.jackson.core.JsonToken.VALUE_TRUE;

public final class InstanceDeserializeStandardValue<T>
  extends InstanceBase<T>
  implements GetDependenciesMixin<DeserializationStepInstance<?>>
{
  private final PropertyConfiguration<T> conf;
  private InstanceGroupMany unmanaged;
  private T data;
  private boolean isPropertyRead = false;

  public InstanceDeserializeStandardValue(
    PropertyConfiguration<T> conf,
    InstanceGroupMany unmanaged
  ) {
    this.unmanaged = unmanaged;
    this.conf = conf;
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    return parser.getCurrentToken().isScalarValue();
  }

  @Override
  public void pushToken(JsonParser parser) throws IOException {
    // Of course, this would be replaced with the current implementation of jackson-databind, which is much
    // more sophisticated than this. We are not supporting dates, among other things.
    final JsonParseException notTheAppropriateType = new JsonParseException(parser, "Not the appropriate type.");
    final JsonToken currentToken = parser.getCurrentToken();
    final Class<T> typeClass = conf.getTypeConfiguration().getTypeClass();
    switch (currentToken) {
      case NOT_AVAILABLE:
        break;
      case VALUE_NULL:
        if (conf.isRequired()) throw new JsonParseException(parser, "Value is required");
        data = null;
        isPropertyRead = true;
        parser.nextToken();
        break;
      case VALUE_FALSE:
      case VALUE_TRUE:
        if (typeClass != Boolean.class) throw notTheAppropriateType;
        //noinspection unchecked
        data = (T) (currentToken == VALUE_TRUE ? Boolean.TRUE : Boolean.FALSE);
        break;
      case VALUE_NUMBER_FLOAT:
      case VALUE_NUMBER_INT:
      case VALUE_STRING:
        final Set<Class<?>> classes = SupportedTypes.tokenToSupportedTypes.get(currentToken);
        if (classes == null) throw notTheAppropriateType;
        //noinspection unchecked
        data = (T) SupportedTypes.typeToValueParser.get(typeClass).apply(parser);
        isPropertyRead = true;
        parser.nextToken();
        break;
      default:
        throw new JsonParseException(parser, "Unexpected token " + parser.getCurrentToken());
    }
  }

  @Override
  public boolean isOptional() {
    return false;
  }

  @Override
  public boolean areDependenciesSatisfied() {
    return getDependencies().stream().allMatch(DeserializationStepInstance::areDependenciesSatisfied);
  }

  @Override
  public boolean isDone() {
    return isPropertyRead && areDependenciesSatisfied();
  }

  @Override
  public void prune(Consumer<DeserializationStepInstance<?>> onDependencyRemoved) {
    if (unmanaged != null) {
      unmanaged.prune(() -> true, onDependencyRemoved, this);
      if (unmanaged.getDependencies().stream().allMatch(DeserializationStepInstance::isDone)) {
        unmanaged = null;
      }
    }
    if (isDone()) {
      new ArrayList<>(getParents()).forEach(p -> p.prune(onDependencyRemoved));
    }
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public DependencyGroups<DeserializationStepInstance<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
