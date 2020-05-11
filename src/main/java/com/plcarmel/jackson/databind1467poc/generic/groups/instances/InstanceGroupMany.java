package com.plcarmel.jackson.databind1467poc.generic.groups.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.GroupMany;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InstanceGroupMany<TInput>
  extends GroupMany<StepInstance<TInput, ?>>
  implements InstanceGroup<TInput>
{
  private final Set<StepInstance<TInput, ?>> removedSteps = new HashSet<>();

  public InstanceGroupMany(boolean isManaged, List<StepInstance<TInput, ?>> list) {
    super(isManaged, list);
  }

  @Override
  public void removeDependencyFromList(StepInstance<TInput, ?> dependency) {
    if (isManaged()) {
      if (getList().contains(dependency)) {
        removedSteps.add(dependency);
      }
    } else {
      getList().remove(dependency);
    }
  }

  @Override
  public boolean anyDone() {
    return getDependencies().stream().anyMatch(StepInstance::isDone);
  }


  @Override
  public boolean allDone() {
    return getDependencies().stream().allMatch(StepInstance::isDone);
  }

  @Override
  public List<StepInstance<TInput, ?>> getDependencies() {
    return super.getDependencies().stream().filter(d -> !removedSteps.contains(d)).collect(Collectors.toList());
  }
}
