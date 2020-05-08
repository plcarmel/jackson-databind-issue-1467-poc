package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;

import java.util.List;
import java.util.stream.Collectors;

public class StepGroupMany<TInput> extends GroupMany<Step<TInput, ?>> {

  public StepGroupMany(List<Step<TInput, ?>> list) {
    super(list);
  }

  public InstanceGroupMany<TInput> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceGroupMany<>(
      getDependencies()
        .stream()
        .map(factory::instantiate)
        .collect(Collectors.toList())
    );
  }
}
