package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstanceGroupTwo<TFirst, TSecond>
  extends GroupTwo<
    DeserializationStepInstance<TFirst>,
    DeserializationStepInstance<TSecond>,
    DeserializationStepInstance<?>
  > implements Prunable
{
  public InstanceGroupTwo(DeserializationStepInstance<TFirst> first, DeserializationStepInstance<TSecond> second) {
    super(first, second);
  }

  @Override
  public void prune(
    Supplier<Boolean> doRemoveDependency,
    Consumer<DeserializationStepInstance<?>> onDependencyRemoved,
    DeserializationStepInstance<?> ref
  ) {
    final boolean firstDone = first != null && first.isDone();
    final boolean secondDone = second != null && second.isDone();
    if ((firstDone || secondDone) && doRemoveDependency.get()) {
      if (firstDone) {
        onDependencyRemoved.accept(first);
        first = null;
      }
      if (secondDone) {
        onDependencyRemoved.accept(second);
        second = null;
      }
    }
  }

}
