package com.plcarmel.jackson.databind1467poc.example.steps;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.jackson.databind1467poc.generic.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceDeserializeStandardValue;
import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.stream.Stream;

public class StepDeserializeStandardValue<TResult>
  implements Step<JsonParser, TResult>, GetDependenciesMixin<Step<JsonParser, ?>>
{
  private final PropertyConfiguration<TResult> conf;
  private final StepGroupMany<JsonParser> unmanaged;

  public StepDeserializeStandardValue(PropertyConfiguration<TResult> conf, StepGroupMany<JsonParser> unmanaged) {
    this.conf = conf;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<JsonParser, TResult> instantiated(InstanceFactory<JsonParser> factory) {
    return new InstanceDeserializeStandardValue<>(conf, unmanaged.instantiated(factory));
  }

  @Override
  public DependencyGroups<Step<JsonParser, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
