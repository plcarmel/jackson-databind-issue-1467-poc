package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DependencyGroups<TDep extends HasDependencies<TDep>> implements HasDependencies<TDep> {

  private final List<HasDependencies<TDep>> groups;

  public DependencyGroups(Stream<HasDependencies<TDep>> groups) {
    this.groups = groups.collect(toList());
  }

  @Override
  public List<TDep> getDependencies() {
    return groups.stream().map(HasDependencies::getDependencies).flatMap(List::stream).collect(toList());
  }
}
