package com.plcarmel.jackson.databind1467poc.example.structures;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.List;

public class StructureUnmanaged<T extends HasDependencies<T>> implements HasDependencies<T> {

  public final List<T> unmanagedDependencies;

  public StructureUnmanaged(List<T> dependencies) {
    this.unmanagedDependencies = dependencies;
  }

  @Override
  public List<T> getUnmanagedDependencies() {
    return unmanagedDependencies;
  }
}
