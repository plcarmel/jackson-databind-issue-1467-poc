package com.plcarmel.jackson.databind1467poc.generic.steps;

import com.plcarmel.jackson.databind1467poc.generic.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.jackson.databind1467poc.generic.groups.Group;
import com.plcarmel.jackson.databind1467poc.generic.groups.steps.StepGroupMany;
import com.plcarmel.jackson.databind1467poc.generic.instances.InstanceInstantiateUsingCreator;
import com.plcarmel.jackson.databind1467poc.theory.CreatorConfiguration;
import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.stream.Stream;

public class StepInstantiateUsingCreator<TInput, TResult>
  implements Step<TInput, TResult>, GetDependenciesMixin<Group<Step<TInput, ?>>, Step<TInput, ?>>
{
  private final CreatorConfiguration<TResult> conf;
  private final StepGroupMany<TInput> parameters;
  private final StepGroupMany<TInput> unmanaged;

  public StepInstantiateUsingCreator(
    CreatorConfiguration<TResult> creatorConf,
    StepGroupMany<TInput> parameters, StepGroupMany<TInput> unmanaged
  ) {
    this.conf = creatorConf;
    this.parameters = parameters;
    this.unmanaged = unmanaged;
  }

  @Override
  public DependencyGroups<Group<Step<TInput, ?>>, Step<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(parameters, unmanaged));
  }

  @Override
  public StepInstance<TInput, TResult> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceInstantiateUsingCreator<>(
      conf,
      parameters.instantiated(factory),
      unmanaged.instantiated(factory)
    );
  }
}
