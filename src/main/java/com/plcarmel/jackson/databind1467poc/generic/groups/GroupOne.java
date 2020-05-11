package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.Collections;
import java.util.List;

public class GroupOne<TMain extends TDep, TDep extends HasDependencies<TDep>>
  extends GroupBase<TDep>
{
  protected TMain sole;

  public TMain get() {
    return sole;
  }

  public GroupOne(TMain sole) {
    super(true);
    this.sole = sole;
  }

  @Override
  public List<TDep> getDependencies() {
    return sole == null ? Collections.emptyList() : Collections.singletonList(sole);
  }
}
