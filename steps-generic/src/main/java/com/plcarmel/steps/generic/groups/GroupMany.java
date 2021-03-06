package com.plcarmel.steps.generic.groups;

import com.plcarmel.steps.theory.HasDependencies;

import java.util.List;

public class GroupMany<TDep extends HasDependencies<TDep>> extends GroupBase<TDep>
{
  private final List<TDep> list;

  public List<TDep> getList() {
    return list;
  }

  public GroupMany(boolean isManaged, List<TDep> list) {
    super(isManaged);
    this.list = list;
  }

  @Override
  public List<TDep> getDependencies() {
    return list;
  }
}
