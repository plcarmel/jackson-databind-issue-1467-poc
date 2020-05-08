package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class InstanceGroupMany<TInput>
  extends GroupMany<StepInstance<TInput, ?>>
  implements InstanceGroup<TInput>
{
  public InstanceGroupMany(List<StepInstance<TInput, ?>> list) {
    super(list);
  }

  @Override
  public void prune(
    Predicate<StepInstance<TInput, ?>> doRemoveDependency,
    Consumer<StepInstance<TInput, ?>> onDependencyRemoved,
    StepInstance<TInput, ?> ref
  ) {
    final List<StepInstance<TInput, ?>> dependencies = getDependencies();
    for (int i = 0; i < dependencies.size(); ) {
      final StepInstance<TInput, ?> d = dependencies.get(i);
      if (d.isDone() && doRemoveDependency.test(d)) {
        d.removeParent(ref);
        dependencies.remove(i);
        onDependencyRemoved.accept(d);
      }
      else i++;
    }
  }
}
