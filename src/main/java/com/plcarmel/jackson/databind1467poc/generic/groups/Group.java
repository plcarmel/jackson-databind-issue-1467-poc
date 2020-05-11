package com.plcarmel.jackson.databind1467poc.generic.groups;

import com.plcarmel.jackson.databind1467poc.theory.HasDependencies;

public interface Group<TDep extends HasDependencies<TDep>> extends HasDependencies<TDep> {

  boolean isManaged();

}
