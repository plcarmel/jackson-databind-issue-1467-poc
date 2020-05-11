package com.plcarmel.jackson.databind1467poc.generic.instances;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

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
