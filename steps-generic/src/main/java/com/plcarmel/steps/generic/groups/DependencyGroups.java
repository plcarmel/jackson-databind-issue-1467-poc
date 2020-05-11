package com.plcarmel.steps.generic.groups;

import com.plcarmel.steps.theory.HasDependencies;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DependencyGroups<TGroup extends HasDependencies<TDep>, TDep extends HasDependencies<TDep>>
  implements HasDependencies<TDep>
{
  private final List<TGroup> groups;

  public List<TGroup> getGroups() {
    return groups;
  }

  public DependencyGroups(Stream<? extends TGroup> groups) {
    this.groups = groups.filter(Objects::nonNull).collect(toList());
  }

  @Override
  public List<TDep> getDependencies() {
    return groups.stream().map(HasDependencies::getDependencies).flatMap(List::stream).collect(toList());
  }

}
