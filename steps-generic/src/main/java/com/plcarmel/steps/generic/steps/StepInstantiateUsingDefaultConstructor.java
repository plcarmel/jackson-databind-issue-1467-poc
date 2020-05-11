package com.plcarmel.steps.generic.steps;

import com.plcarmel.steps.generic.groups.DependencyGroups;
import com.plcarmel.steps.generic.groups.mixins.GetDependenciesMixin;
import com.plcarmel.steps.generic.groups.Group;
import com.plcarmel.steps.generic.groups.steps.StepGroupMany;
import com.plcarmel.steps.generic.instances.InstanceInstantiateUsingDefaultConstructor;
import com.plcarmel.steps.theory.InstanceFactory;
import com.plcarmel.steps.theory.Step;
import com.plcarmel.steps.theory.StepInstance;
import com.plcarmel.steps.theory.TypeConfiguration;

import java.util.stream.Stream;

public class StepInstantiateUsingDefaultConstructor<TInput, TResult>
  implements
    Step<TInput, TResult>,
    GetDependenciesMixin<Group<Step<TInput, ?>>, Step<TInput, ?>>
{
  private final TypeConfiguration<TResult> conf;
  private final StepGroupMany<TInput> unmanaged;

  public StepInstantiateUsingDefaultConstructor(TypeConfiguration<TResult> conf, StepGroupMany<TInput> unmanaged) {
    this.conf = conf;
    this.unmanaged = unmanaged;
  }

  @Override
  public StepInstance<TInput, TResult> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceInstantiateUsingDefaultConstructor<>(conf, unmanaged.instantiated(factory));
  }

  @Override
  public DependencyGroups<Group<Step<TInput, ?>>, Step<TInput, ?>> getDependencyGroups() {
    return new DependencyGroups<>(Stream.of(unmanaged));
  }
}
