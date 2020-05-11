package com.plcarmel.steps.theory;

import java.util.List;

public interface HasDependencies<T extends HasDependencies<T>> {

  // First dependencies in the list have a higher priority
  List<T> getDependencies();

}
