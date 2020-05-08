package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstanceGroupTwo<TInput, TFirst, TSecond>
  extends GroupTwo<
  StepInstance<TInput, TFirst>,
  StepInstance<TInput, TSecond>,
  StepInstance<TInput, ?>
  > implements InstanceGroup<TInput>
{
  public InstanceGroupTwo(
    StepInstance<TInput, TFirst> first,
    StepInstance<TInput, TSecond> second
  ) {
    super(first, second);
  }

  @Override
  public void prune(
    Supplier<Boolean> doRemoveDependency,
    Consumer<StepInstance<TInput, ?>> onDependencyRemoved,
    StepInstance<TInput, ?> ref
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
