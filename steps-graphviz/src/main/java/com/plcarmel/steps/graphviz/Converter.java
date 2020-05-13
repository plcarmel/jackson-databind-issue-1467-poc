package com.plcarmel.steps.graphviz;

import com.plcarmel.steps.theory.HasDependencies;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.Node;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public interface Converter<TDep extends HasDependencies<TDep>> {

  default boolean canConvert(TDep stepInstance) {
    return true;
  }

  default Node getNode(TDep stepInstance, Supplier<String> newId) {
    return Factory
      .node(newId.get())
      .with(Label.of(String.format("%s", stepInstance.getClass().getSimpleName())));
  }

  default Graph addNodeLinks(
    Graph g,
    TDep stepInstance,
    Function<TDep, Node> getNode
  ) {
    final Node current = getNode.apply(stepInstance);
    return g.with(
      stepInstance
        .getDependencies()
        .stream()
        .map(getNode)
        .map(Link::to)
        .map(current::link)
        .collect(toList())
    );
  }

}
