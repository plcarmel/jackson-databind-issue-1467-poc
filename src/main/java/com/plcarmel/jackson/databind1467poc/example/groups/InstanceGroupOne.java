package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstanceGroupOne<T>
  extends GroupOne<DeserializationStepInstance<T>, DeserializationStepInstance<?>>
  implements InstanceGroup
{
  public InstanceGroupOne(DeserializationStepInstance<T> main) {
    super(main);
  }

  @Override
  public void prune(
    Supplier<Boolean> doRemoveDependency,
    Consumer<DeserializationStepInstance<?>> onDependencyRemoved,
    DeserializationStepInstance<?> ref
  ) {
    if (main != null && main.isDone() && doRemoveDependency.get()) {
      main.removeParent(ref);
      onDependencyRemoved.accept(main);
      main = null;
    }
  }
}
