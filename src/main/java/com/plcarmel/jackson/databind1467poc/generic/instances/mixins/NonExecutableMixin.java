package com.plcarmel.jackson.databind1467poc.generic.instances.mixins;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

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
