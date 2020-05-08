package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class InstanceGroupOne<TInput, TResult>
  extends GroupOne<StepInstance<TInput, TResult>, StepInstance<TInput, ?>>
  implements InstanceGroup<TInput>
{
  public InstanceGroupOne(StepInstance<TInput, TResult> sole) {
    super(sole);
  }

  @Override
  public void prune(
    Predicate<StepInstance<TInput, ?>> doRemoveDependency,
    Consumer<StepInstance<TInput, ?>> onDependencyRemoved,
    StepInstance<TInput, ?> ref
  ) {
    if (sole != null && sole.isDone() && doRemoveDependency.test(sole)) {
      sole.removeParent(ref);
      onDependencyRemoved.accept(sole);
      sole = null;
    }
  }
}
