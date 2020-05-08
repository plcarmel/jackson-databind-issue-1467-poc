package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.Collections;
import java.util.List;

public class GroupOne<TMain extends TDep, TDep extends HasDependencies<TDep>>
  implements HasDependencies<TDep>
{
  protected TMain main;

  public TMain getMain() {
    return main;
  }

  public GroupOne(TMain main) {
    this.main = main;
  }

  @Override
  public List<TDep> getDependencies() {
    return main == null ? Collections.emptyList() : Collections.singletonList(main);
  }
}
