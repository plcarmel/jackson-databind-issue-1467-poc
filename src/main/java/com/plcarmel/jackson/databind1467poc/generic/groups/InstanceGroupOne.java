package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.StepInstance;

import java.util.Collections;
import java.util.List;

public class InstanceGroupOne<TInput, TResult>
  extends GroupOne<StepInstance<TInput, TResult>, StepInstance<TInput, ?>>
  implements InstanceGroup<TInput>
{
  boolean isRemovedFromList; // Dependency saved only as a data source

  public InstanceGroupOne(StepInstance<TInput, TResult> sole) {
    super(sole);
  }

  @Override
  public void removeDependencyFromList(StepInstance<TInput, ?> dependency) {
    if (sole == dependency) isRemovedFromList = true;
  }

  @Override
  public List<StepInstance<TInput, ?>> getDependencies() {
    return isRemovedFromList ? Collections.emptyList() : super.getDependencies();
  }

  @Override
  public boolean anyDone() {
    return isRemovedFromList || sole.isDone();
  }

  @Override
  public boolean allDone() {
    return anyDone();
  }

}
