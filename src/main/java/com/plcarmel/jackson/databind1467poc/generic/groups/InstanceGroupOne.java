package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstanceGroupOne<TInput, TResult>
  extends GroupOne<StepInstance<TInput, TResult>, StepInstance<TInput, ?>>
  implements InstanceGroup<TInput>
{
  public InstanceGroupOne(StepInstance<TInput, TResult> main) {
    super(main);
  }

  @Override
  public void prune(
    Supplier<Boolean> doRemoveDependency,
    Consumer<StepInstance<TInput, ?>> onDependencyRemoved,
    StepInstance<TInput, ?> ref
  ) {
    if (main != null && main.isDone() && doRemoveDependency.get()) {
      main.removeParent(ref);
      onDependencyRemoved.accept(main);
      main = null;
    }
  }
}
