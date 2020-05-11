package com.plcarmel.steps.generic.groups.steps;

import com.plcarmel.steps.generic.groups.GroupOne;
import com.plcarmel.steps.generic.groups.instances.InstanceGroupOne;
import com.plcarmel.steps.theory.InstanceFactory;
import com.plcarmel.steps.theory.Step;

public class StepGroupOne<TInput, TResult> extends GroupOne<Step<TInput, TResult>, Step<TInput, ?>> {

  public StepGroupOne(Step<TInput, TResult> deserializationStep) {
    super(deserializationStep);
  }

  public InstanceGroupOne<TInput, TResult> instantiated(InstanceFactory<TInput> factory) {
    return new InstanceGroupOne<>(factory.instantiate(get()));
  }
}
