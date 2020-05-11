package com.plcarmel.steps.theory;

public interface DependencyBuilder<
  Td extends HasDependencies<Td>,
  Tb extends HasDependencies<?>
> extends HasDependencies<Td> {

}
