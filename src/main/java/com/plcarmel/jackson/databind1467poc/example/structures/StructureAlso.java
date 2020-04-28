package com.plcarmel.jackson.databind1467poc.example.structures;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.ArrayList;
import java.util.List;

public class StructureAlso<TMain extends TDep, TDep extends HasDependencies<TDep>>
  extends StructureUnmanaged<TDep>
{
  protected TMain mainDependency;

  public StructureAlso(TMain mainDependency, List<TDep> dependencies) {
    super(dependencies);
    this.mainDependency = mainDependency;
  }

  @Override
  public List<TDep> getUnmanagedDependencies() {
    final List<TDep> result = new ArrayList<>();
    result.add(mainDependency);
    result.addAll(super.unmanagedDependencies);
    return result;
  }
}
