package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.List;

public class GroupMany<T extends HasDependencies<T>>
  implements HasDependencies<T>
{
  private final List<T> list;

  public List<T> getList() {
    return list;
  }

  public GroupMany(List<T> list) {
    this.list = list;
  }

  @Override
  public List<T> getDependencies() {
    return list;
  }
}
