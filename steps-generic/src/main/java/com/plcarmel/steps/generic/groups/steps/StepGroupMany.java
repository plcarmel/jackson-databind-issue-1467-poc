package com.plcarmel.steps.generic.groups.steps;

import com.plcarmel.steps.generic.groups.GroupMany;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.steps.theory.InstanceFactory;
import com.plcarmel.steps.theory.Step;

import java.util.List;
import java.util.stream.Collectors;

public class StepGroupMany<TInput> extends GroupMany<Step<TInput, ?>> {

  public StepGroupMany(boolean isManaged, List<Step<TInput, ?>> list) {
    super(isManaged, list);
  }

  public InstanceGroupMany<TInput> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceGroupMany<>(
      isManaged(),
      getDependencies()
        .stream()
        .map(factory::instantiate)
        .collect(Collectors.toList())
    );
  }
}
