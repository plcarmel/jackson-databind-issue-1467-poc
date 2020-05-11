package com.plcarmel.steps.generic.groups.mixins;

import com.plcarmel.steps.generic.groups.DependencyGroups;
import com.plcarmel.steps.theory.HasDependencies;

import java.util.List;

public interface GetDependenciesMixin<TGroup extends HasDependencies<TDep>, TDep extends HasDependencies<TDep>>
  extends HasDependencies<TDep>
{
  DependencyGroups<TGroup, TDep> getDependencyGroups();

  @Override
  default List<TDep> getDependencies() {
    return getDependencyGroups().getDependencies();
  }
}
