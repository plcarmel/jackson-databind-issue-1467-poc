package com.plcarmel.steps.generic.instances.mixins;

import com.plcarmel.steps.theory.StepInstance;

public interface NonExecutableMixin<TInput, TResult> extends StepInstance<TInput, TResult> {

  @Override
  default Boolean isReadyToBeExecuted() {
    return null;
  }

  @Override
  default Boolean hasBeenExecuted() {
    return null;
  }

  @Override
  default void execute() {}
}
