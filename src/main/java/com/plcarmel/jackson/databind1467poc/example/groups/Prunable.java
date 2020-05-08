package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Prunable {

  void prune(
    Supplier<Boolean> doRemoveDependency,
    Consumer<DeserializationStepInstance<?>> onDependencyRemoved,
    DeserializationStepInstance<?> ref
  );

}
