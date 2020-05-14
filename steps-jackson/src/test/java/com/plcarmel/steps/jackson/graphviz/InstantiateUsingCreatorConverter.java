package com.plcarmel.steps.jackson.graphviz;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.generic.instances.InstanceInstantiateUsingCreator;
import com.plcarmel.steps.graphviz.InstanceConverter;
import com.plcarmel.steps.theory.CreatorConfiguration;
import com.plcarmel.steps.theory.PropertyConfiguration;
import com.plcarmel.steps.theory.StepInstance;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.Node;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InstantiateUsingCreatorConverter extends InstanceConverter<JsonParser> {

  @Override
  public boolean canConvert(StepInstance<JsonParser, ?> stepInstance) {
    return stepInstance instanceof InstanceInstantiateUsingCreator;
  }

  @Override
  public Node getNode(StepInstance<JsonParser, ?> stepInstance, Supplier<String> newId) {
    final String id = newId.get();
    //noinspection unchecked
    final InstanceInstantiateUsingCreator<JsonParser, ?> instantiate =
      (InstanceInstantiateUsingCreator<JsonParser, ?>) stepInstance;
    final CreatorConfiguration<?> conf = instantiate.getConf();
    final Node node =
      Factory
        .node(id)
        .with(
          Label.of(
            String.format(
              "new %s(%s)",
              conf
                .getType()
                .getSimpleName(),
              conf
                .getParamConfigurations()
                .stream()
                .map(PropertyConfiguration::getName)
                .collect(Collectors.joining(", "))
            )
          ),
          Font.name("monospace"),
          Font.size(10)
        );
    return applyDefaultStyles(stepInstance, node);
  }
}
