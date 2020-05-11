package com.plcarmel.steps.generic.builders;

import com.plcarmel.steps.theory.Step;
import com.plcarmel.steps.theory.StepBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class BuilderHavingUnmanagedDependencies<TInput, TResult> implements StepBuilder<TInput, TResult> {

  private final List<Step<TInput, ?>> dependencies = new ArrayList<>();

  @Override
  public void addDependency(Step<TInput, ?> dependency) {
    dependencies.add(dependency);
  }

  @Override
  public List<Step<TInput, ?>> getDependencies() {
    return dependencies;
  }
}
