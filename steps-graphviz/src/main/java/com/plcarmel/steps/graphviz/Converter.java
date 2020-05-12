package com.plcarmel.steps.graphviz;

import com.plcarmel.steps.theory.HasDependencies;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.Node;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public interface Converter<TDep extends HasDependencies<TDep>> {

  default boolean canConvert(HasDependencies<TDep> stepInstance) {
    return true;
  }

  default Node getNode(HasDependencies<TDep> stepInstance, Supplier<String> newId) {
    final String id = newId.get();
    return Factory
      .node(id)
      .with(Label.of(String.format("[%s] %s", id, stepInstance.getClass().getSimpleName())));
  }

  default Graph addNodeLinks(
    Graph g,
    HasDependencies<TDep> stepInstance,
    Function<HasDependencies<TDep>, Node> getNode
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
