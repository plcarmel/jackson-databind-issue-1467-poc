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

  Converter<TDep> getConverter(TDep step);

  Supplier<String> getIdGenerator();

  default void generateNodes(Map<TDep, Node> nodes, TDep currentStep) {
    nodes.put(currentStep, getConverter(currentStep).getNode(currentStep, getIdGenerator()));
    currentStep.getDependencies().forEach(d -> generateNodes(nodes, d));
  }

  default Map<TDep, Node> generateNodes(TDep finalStep) {
    final Map<TDep, Node> result = new HashMap<>();
    generateNodes(result, finalStep);
    return result;
  }

  default Graph linkGraph(
    Function<TDep, Node> getNode,
    Graph g,
    TDep currentStep
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

  default Graph generateGraph(Graph g, TDep finalStep) {
    final Map<TDep, Node> nodes = generateNodes(finalStep);
    return linkGraph(nodes::get, g, finalStep);
  }

  default Graph generateGraph(TDep finalStep) {
    return generateGraph(Factory.graph().directed(), finalStep);
  }

}
