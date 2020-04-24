package com.plcarmel.jackson.databind1467poc;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

  public static <T> List<T> topologicalSort(T startingPoint, Function<T, Collection<T>> getNodes) {
    final List<T> result = new ArrayList<>();
    final Set<T> visited = new HashSet<>();
    List<T> lastElements = Collections.singletonList(startingPoint);
    while (!lastElements.isEmpty()) {
      result.addAll(lastElements);
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
