package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepInstance;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstanceGroupMany extends GroupMany<DeserializationStepInstance<?>> implements InstanceGroup {

  public InstanceGroupMany(List<DeserializationStepInstance<?>> list) {
    super(list);
  }

  @Override
  public void prune(
    Supplier<Boolean> doRemoveDependency,
    Consumer<DeserializationStepInstance<?>> onDependencyRemoved,
    DeserializationStepInstance<?> ref
  ) {
    final List<DeserializationStepInstance<?>> dependencies = getDependencies();
    for (int i = 0; i < dependencies.size(); ) {
      final DeserializationStepInstance<?> d = dependencies.get(i);
      if (d.isDone() && doRemoveDependency.get()) {
        d.removeParent(ref);
        dependencies.remove(i);
        onDependencyRemoved.accept(d);
      }
      else i++;
    }
  }
}
