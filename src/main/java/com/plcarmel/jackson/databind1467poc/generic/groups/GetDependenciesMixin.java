package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.List;

public interface GetDependenciesMixin<TDep extends HasDependencies<TDep>> extends HasDependencies<TDep> {

  DependencyGroups<TDep> getDependencyGroups();

  @Override
  default List<TDep> getDependencies() {
    return getDependencyGroups().getDependencies();
  }
}
