package com.plcarmel.steps.jackson.steps;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.generic.groups.DependencyGroups;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.Group;
import com.plcarmel.steps.generic.groups.steps.StepGroupMany;
import com.plcarmel.steps.jackson.instances.InstanceDeserializeStandardValue;
import com.plcarmel.steps.theory.InstanceFactory;
import com.plcarmel.steps.theory.Step;
import com.plcarmel.steps.theory.StepInstance;
import com.plcarmel.steps.theory.PropertyConfiguration;

import java.util.stream.Stream;

public class StepDeserializeStandardValue<TResult>
  implements Step<JsonParser, TResult>, GetDependenciesMixin<Group<Step<JsonParser, ?>>, Step<JsonParser, ?>>
{
  private final PropertyConfiguration<?, TResult> conf;
  private final StepGroupMany<JsonParser> unmanaged;

  public StepDeserializeStandardValue(PropertyConfiguration<?, TResult> conf, StepGroupMany<JsonParser> unmanaged) {
    this.conf = conf;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<JsonParser, TResult> instantiated(InstanceFactory<JsonParser> factory) {
    return new InstanceDeserializeStandardValue<>(conf, unmanaged.instantiated(factory));
  }

  @Override
  public DependencyGroups<Group<Step<JsonParser, ?>>, Step<JsonParser, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
