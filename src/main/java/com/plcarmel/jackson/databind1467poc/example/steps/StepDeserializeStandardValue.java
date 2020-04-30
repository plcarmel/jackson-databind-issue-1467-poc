package com.plcarmel.jackson.databind1467poc.example.steps;

import com.plcarmel.jackson.databind1467poc.example.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.example.groups.HasDependencyGroupsMixin;
import com.plcarmel.jackson.databind1467poc.example.groups.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.example.instances.InstanceDeserializeStandardValue;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;
import com.plcarmel.jackson.databind1467poc.theory.PropertyConfiguration;

import java.util.stream.Stream;

public class StepDeserializeStandardValue<T>
  implements DeserializationStep<T>, HasDependencyGroupsMixin<DeserializationStep<?>>
{
  private final PropertyConfiguration<T> conf;
  private final StepGroupMany unmanaged;

  public StepDeserializeStandardValue(PropertyConfiguration<T> conf, StepGroupMany unmanaged) {
    this.conf = conf;
    this.unmanaged = unmanaged;
  }

  @Override
  public DeserializationStepInstance<T> instantiated(DeserializationStep.InstanceFactory factory) {
    return new InstanceDeserializeStandardValue<>(conf, unmanaged.instantiated(factory));
  }

  @Override
  public DependencyGroups<DeserializationStep<?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
