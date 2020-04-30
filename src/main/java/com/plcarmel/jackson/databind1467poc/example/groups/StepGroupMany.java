package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;
import java.util.stream.Collectors;

public class StepGroupMany extends GroupMany<DeserializationStep<?>> {

  public StepGroupMany(List<DeserializationStep<?>> list) {
    super(list);
  }

  public List<DeserializationStepInstance<?>> instantiated(DeserializationStep.InstanceFactory factory) {
    return getDependencies()
      .stream()
      .map(factory::instantiate)
      .collect(Collectors.toList());
  }
}
