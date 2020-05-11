package com.plcarmel.steps.generic.groups;

import com.plcarmel.steps.theory.HasDependencies;

public interface Group<TDep extends HasDependencies<TDep>> extends HasDependencies<TDep> {

  boolean isManaged();

}
