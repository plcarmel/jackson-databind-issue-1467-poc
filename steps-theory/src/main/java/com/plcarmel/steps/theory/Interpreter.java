package com.plcarmel.steps.theory;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Main algorithm, it dispatches each token to the appropriate node (execution step) of the
// dependency graph, starting with the leaves. If no leave can handle the current token, the
// level before the leaves if probed, and so on.
public class Interpreter<TInput, TResult> implements AsynchronousDeserialization<TInput, TResult> {

  private final List<StepInstance<TInput, ?>> startToFinish;
  private final StepInstance<TInput, TResult> finalStep;

  public Interpreter(Step<TInput, TResult> finalStep) {
    this.finalStep = finalStep.instantiated();
    startToFinish = topologicalSort(this.finalStep, HasDependencies::getDependencies);
    startToFinish.forEach(StepInstance::registerAsParent);
    getLeaves().forEach(s -> s.recurse(startToFinish::remove));
  }

  @Override
  public boolean canHandleCurrentToken(TInput input) {
    // not meant to be performant
    return startToFinish.stream().anyMatch(d -> d.canHandleCurrentToken(input));
  }

  @Override
  public void pushToken(TInput parser) throws IOException {
    final StepInstance<TInput, ?> step =
      startToFinish
        .stream()
        .filter(d -> d.canHandleCurrentToken(parser))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No step can handle current token"));
    step.pushToken(parser);
    step.recurse(startToFinish::remove);
  }

  @Override
  public boolean isDone() {
    return finalStep.isDone();
  }

  @Override
  public TResult getData() {
    return finalStep.getData();
  }

  private static <T> List<T> topologicalSort(T startingPoint, Function<T, Collection<T>> getNodes) {
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

  public void eof() {
    new ArrayList<>(startToFinish).forEach(d -> d.clean(x -> {}, true));
    getLeaves().forEach(d -> d.recurse(x -> {}));
  }

  private Stream<StepInstance<TInput, ?>> getLeaves() {
    return new ArrayList<>(startToFinish)
      .stream()
      .filter(d -> d.getDependencies().isEmpty());
  }
}
