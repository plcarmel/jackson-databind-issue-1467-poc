package com.plcarmel.jackson.databind1467poc.example.builders;

import com.plcarmel.jackson.databind1467poc.theory.DeserializationStep;
import com.plcarmel.jackson.databind1467poc.theory.DeserializationStepBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class BuilderHavingUnmanagedDependencies<T> implements DeserializationStepBuilder<T> {

  private final List<DeserializationStep<?>> dependencies = new ArrayList<>();

  @Override
  public void addDependency(DeserializationStep<?> dependency) {
    dependencies.add(dependency);
  }

  @Override
  public List<DeserializationStep<?>> getDependencies() {
    return dependencies;
  }
}
