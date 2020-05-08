package com.plcarmel.jackson.databind1467poc.generic.builders;

import com.plcarmel.jackson.databind1467poc.theory.Step;
import com.plcarmel.jackson.databind1467poc.theory.StepBuilder;

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
