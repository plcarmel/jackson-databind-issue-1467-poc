package com.plcarmel.steps.graphviz;

import com.plcarmel.steps.theory.HasDependencies;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public interface GraphGenerator<TDep extends HasDependencies<TDep>> {

  Converter<TDep> getConverter(HasDependencies<TDep> step);

  Supplier<String> getIdGenerator();

  default void generateNodes(Map<HasDependencies<TDep>, Node> nodes, HasDependencies<TDep> currentStep) {
    nodes.put(currentStep, getConverter(currentStep).getNode(currentStep, getIdGenerator()));
    currentStep.getDependencies().forEach(d -> generateNodes(nodes, d));
  }

  default Map<HasDependencies<TDep>, Node> generateNodes(HasDependencies<TDep> finalStep) {
    final Map<HasDependencies<TDep>, Node> result = new HashMap<>();
    generateNodes(result, finalStep);
    return result;
  }

  default Graph linkGraph(
    Function<HasDependencies<TDep>, Node> getNode,
    Graph g,
    HasDependencies<TDep> currentStep
  ) {
    return currentStep
      .getDependencies()
      .stream()
      .reduce(
        getConverter(currentStep).addNodeLinks(g, currentStep, getNode),
        (g2, d) -> linkGraph(getNode, g2, d),
        (x, y) -> { throw new RuntimeException("Combiner should no be called."); }
      );
  }

  default Graph generateGraph(Graph g, HasDependencies<TDep> finalStep) {
    final Map<HasDependencies<TDep>, Node> nodes = generateNodes(finalStep);
    return linkGraph(nodes::get, g, finalStep);
  }

  default Graph generateGraph(HasDependencies<TDep> finalStep) {
    return generateGraph(Factory.graph().directed(), finalStep);
  }

}
