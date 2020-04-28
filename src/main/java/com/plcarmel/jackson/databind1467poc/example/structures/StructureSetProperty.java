package com.plcarmel.jackson.databind1467poc.example.structures;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.ArrayList;
import java.util.List;

public class StructureSetProperty<
  TInstantiate extends TDep,
  TDeserialize extends TDep,
  TDep extends HasDependencies<TDep>
> extends StructureUnmanaged<TDep> {

  protected TInstantiate instantiationStep;
  protected TDeserialize deserializationStep;

  public StructureSetProperty(
    TInstantiate instantiationStep,
    TDeserialize deserializationStep,
    List<TDep> dependencies
  ) {
    super(dependencies);
    this.instantiationStep = instantiationStep;
    this.deserializationStep = deserializationStep;
  }

  @Override
  public List<TDep> getUnmanagedDependencies() {
    final List<TDep> result = new ArrayList<>();
    result.add(instantiationStep);
    result.add(deserializationStep);
    result.addAll(super.getUnmanagedDependencies());
    return result;
  }
}
