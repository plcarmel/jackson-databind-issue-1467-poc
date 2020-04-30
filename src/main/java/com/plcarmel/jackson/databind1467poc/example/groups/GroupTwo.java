package com.plcarmel.jackson.databind1467poc.example.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

import java.util.ArrayList;
import java.util.List;

public class GroupTwo<TFirst extends TDep, TSecond extends TDep, TDep extends HasDependencies<TDep>>
  implements HasDependencies<TDep>
{
  private final TFirst first;
  private final TSecond second;

  public TFirst getFirst() {
    return first;
  }

  public TSecond getSecond() {
    return second;
  }

  public GroupTwo(TFirst first, TSecond second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public List<TDep> getDependencies() {
    final List<TDep> result = new ArrayList<>();
    result.add(first);
    result.add(second);
    return result;
  }
}
