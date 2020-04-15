package com.fasterxml.jackson.databind1467poc;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

  static <T> List<List<T>> topologicalSort(T startingPoint, Function<T, Collection<T>> getNodes) {
    final List<List<T>> result = new ArrayList<>();
    final Set<T> visited = new HashSet<>();
    List<T> lastElements = Collections.singletonList(startingPoint);
    while (!lastElements.isEmpty()) {
      result.add(lastElements);
      visited.addAll(lastElements);
      lastElements =
        lastElements
          .stream()
          .flatMap(d -> getNodes.apply(d).stream())
          .filter(d -> !visited.contains(d))
          .collect(Collectors.toList());
    }
    Collections.reverse(result);
    return result;
  }

}
