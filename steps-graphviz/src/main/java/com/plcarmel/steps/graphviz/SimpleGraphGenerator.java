package com.plcarmel.steps.graphviz;

import com.plcarmel.steps.theory.HasDependencies;

import java.util.function.Supplier;

public class SimpleGraphGenerator<TDep extends HasDependencies<TDep>> implements GraphGenerator<TDep> {

  private final Converter<TDep> simpleConverter = new Converter<TDep>() { };

  private int i = 0;

  @Override
  public Converter<TDep> getConverter(HasDependencies<TDep> step) {
    return simpleConverter;
  }

  @Override
  public Supplier<String> getIdGenerator() {
    return () -> "node" + (++i);
  }
}
