package com.plcarmel.jackson.databind1467poc.theory;

import java.util.List;

public interface HasDependencies<T extends HasDependencies<T>> {

  // First dependencies in the list have a higher priority
  List<T> getUnmanagedDependencies();

}
