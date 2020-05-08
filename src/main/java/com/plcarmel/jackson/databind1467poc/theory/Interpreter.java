package com.plcarmel.jackson.databind1467poc.theory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.plcarmel.jackson.databind1467poc.Utils.topologicalSort;

// Main algorithm, it dispatches each token to the appropriate node (execution step) of the
// dependency graph, starting with the leaves. If no leave can handle the current token, the
// level before the leaves if probed, and so on.
public class Interpreter<TInput, TResult> implements AsynchronousDeserialization<TInput, TResult> {

  private final List<StepInstance<TInput, ?>> startToFinish;
  private final StepInstance<TInput, TResult> finalStep;

  public Interpreter(Step<TInput, TResult> finalStep) {
    this.finalStep = finalStep.instantiated();
    this.finalStep.setTreeParents();
    startToFinish = topologicalSort(this.finalStep, HasDependencies::getDependencies);
    new ArrayList<>(startToFinish)
      .stream()
      .filter(d -> d.getDependencies().isEmpty())
      .forEach(d -> d.prune(startToFinish::remove));
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
    if (step.isDone()) {
      new ArrayList<>(step.getParents()).forEach(p -> p.prune(startToFinish::remove));
    }
  }

  @Override
  public boolean isDone() {
    return startToFinish.size() == 1;
  }

  @Override
  public TResult getData() {
    return finalStep.getData();
  }
}
