package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;

import java.util.List;
import java.util.stream.Collectors;

public class StepGroupMany extends GroupMany<DeserializationStep<?>> {

  public StepGroupMany(List<DeserializationStep<?>> list) {
    super(list);
  }

  public InstanceGroupMany instantiated(DeserializationStep.InstanceFactory factory) {
    return new InstanceGroupMany(
      getDependencies()
        .stream()
        .map(factory::instantiate)
        .collect(Collectors.toList())
    );
  }
}
