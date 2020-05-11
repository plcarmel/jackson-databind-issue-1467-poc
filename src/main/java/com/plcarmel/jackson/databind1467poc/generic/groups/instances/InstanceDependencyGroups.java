package com.plcarmel.jackson.databind1467poc.generic.groups.instances;

import com.plcarmel.jackson.databind1467poc.generic.groups.DependencyGroups;
import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InstanceDependencyGroups<TInput>
  extends DependencyGroups<InstanceGroup<TInput>, StepInstance<TInput, ?>>
{
  public InstanceDependencyGroups(Stream<InstanceGroup<TInput>> groups) {
    super(groups);
  }

  public List<StepInstance<TInput, ?>> getDependencies() {
    return getGroups().stream().flatMap(g -> g.getDependencies().stream()).collect(toList());
  }

  public void removeDependencyFromList(StepInstance<TInput, ?> dependency) {
    getGroups().forEach(g -> g.removeDependencyFromList(dependency));
  }

  public void clean(StepInstance<TInput, ?> stepInstance, Consumer<StepInstance<TInput, ?>> onDependencyRemoved) {
    getGroups().forEach(g -> g.clean(stepInstance, onDependencyRemoved));
  }

  public boolean allDone() {
    return getGroups().stream().allMatch(InstanceGroup::allDone);
  }
}
