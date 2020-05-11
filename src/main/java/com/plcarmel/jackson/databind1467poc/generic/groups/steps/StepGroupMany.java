package com.plcarmel.jackson.databind1467poc.generic.groups.steps;

import com.plcarmel.jackson.databind1467poc.generic.groups.GroupMany;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupMany;
import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;

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
