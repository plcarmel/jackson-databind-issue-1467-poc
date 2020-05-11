package com.plcarmel.jackson.databind1467poc.generic.groups.steps;

import com.plcarmel.jackson.databind1467poc.generic.groups.GroupOne;
import com.plcarmel.jackson.databind1467poc.generic.groups.instances.InstanceGroupOne;
import com.plcarmel.jackson.databind1467poc.theory.InstanceFactory;
import com.plcarmel.jackson.databind1467poc.theory.Step;

public class StepGroupOne<TInput, TResult> extends GroupOne<Step<TInput, TResult>, Step<TInput, ?>> {

  public StepGroupOne(Step<TInput, TResult> deserializationStep) {
    super(deserializationStep);
  }

  public InstanceGroupOne<TInput, TResult> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceGroupOne<>(factory.instantiate(get()));
  }
}
