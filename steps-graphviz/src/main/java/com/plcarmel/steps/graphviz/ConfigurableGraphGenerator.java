package com.plcarmel.steps.graphviz;

import com.plcarmel.steps.theory.HasDependencies;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ConfigurableGraphGenerator<TDep extends HasDependencies<TDep>> implements GraphGenerator<TDep> {

  private final List<Converter<TDep>> converters;

  public ConfigurableGraphGenerator(Stream<Converter<TDep>> converters) {
    this.converters = Stream.of(converters, Stream.of(new Converter<TDep>() {})).flatMap(l -> l).collect(toList());
  }

  public ConfigurableGraphGenerator() {
    this(Stream.empty());
  }

  private int i = 0;

  @Override
  public Converter<TDep> getConverter(TDep step) {
    return converters
      .stream()
      .filter(c -> c.canConvert(step))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("No converter found for step."));
  }

  @Override
  public Supplier<String> getIdGenerator() {
    return () -> "node" + (++i);
  }
}
