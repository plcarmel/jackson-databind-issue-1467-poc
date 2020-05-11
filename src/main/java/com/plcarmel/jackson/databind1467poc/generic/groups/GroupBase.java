package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

public abstract class GroupBase<TDep extends HasDependencies<TDep>> implements Group<TDep> {

  private final boolean isManaged;

  protected GroupBase(boolean isManaged) {
    this.isManaged = isManaged;
  }

  @Override
  public boolean isManaged() {
    return isManaged;
  }
}
