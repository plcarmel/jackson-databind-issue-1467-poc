package com.plcarmel.jackson.databind1467poc.theory;

import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.plcarmel.jackson.databind1467poc.Utils.topologicalSort;

// Main algorithm, it dispatch each token to the appropriate node (execution step) of the
// dependency graph, starting with the leaves. If no leave can handle the current token, the
// level before the leaves if probed, and so on.
public class Interpreter<T> implements AsynchronousDeserialization<T> {

  private final List<DeserializationStepInstance<?>> startToFinish;
  private final DeserializationStepInstance<T> finalStep;

  public Interpreter(DeserializationStep<T> finalStep) {
    this.finalStep = finalStep.instantiated();
    this.finalStep.setTreeParents();
    startToFinish = topologicalSort(this.finalStep, HasDependencies::getUnmanagedDependencies);
    new ArrayList<>(startToFinish)
      .stream()
      .filter(d -> d.getUnmanagedDependencies().isEmpty())
      .forEach(d -> d.prune(startToFinish::remove));
  }

  @Override
  public boolean canHandleCurrentToken(JsonParser parser) {
    // not meant to be performant
    return startToFinish.stream().anyMatch(d -> d.canHandleCurrentToken(parser));
  }

  @Override
  public void pushToken(JsonParser parser) throws IOException {
    final DeserializationStepInstance<?> step =
      startToFinish
        .stream()
        .filter(d -> d.canHandleCurrentToken(parser))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No step can handle current token " + parser.getCurrentToken()));
    step.pushToken(parser);
    if (step.isDone()) {
      new ArrayList<>(step.getParents()).forEach(p -> p.prune(startToFinish::remove));
    }
  }

  @Override
  public boolean isDone() {
    return startToFinish.size() == 1;
  }

  @Override
  public T getData() {
    return finalStep.getData();
  }
}
