package com.plcarmel.steps.generic.instances.mixins;

import com.plcarmel.steps.theory.StepInstance;

public interface NoTokenMixin<TInput, TResult> extends StepInstance<TInput, TResult> {

  @Override
  default boolean canHandleCurrentToken(TInput parser) {
    return false;
  }

  @Override
  default void pushToken(TInput input) {
    throw new RuntimeException("Method should not be called.");
  }

  @Override
  default boolean hasTokenBeenReceived() { return true; }
}
