package com.fasterxml.jackson.databind1467poc.theory;

public interface DependencyBuilder<
  Td extends HasDependencies<Td>,
  Tb extends HasDependencies<?>
> extends HasDependencies<Td> {

}
