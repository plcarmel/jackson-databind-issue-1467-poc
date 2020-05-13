package com.plcarmel.steps.jackson.graphviz;

import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.graphviz.ConfigurableGraphGenerator;
import com.plcarmel.steps.graphviz.InstanceConverter;
import com.plcarmel.steps.theory.StepInstance;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class GraphRenderer {

  private int i = 0;

  private final static Format GRAPH_FORMAT = Format.SVG;

  public void printGraph(StepInstance<JsonParser, ?> finalStep) {
    final ConfigurableGraphGenerator<StepInstance<JsonParser, ?>> graphGenerator =
      new ConfigurableGraphGenerator<>(
        Stream.of(
          new SetPropertyConverter(),
          new AlsoConverter(),
          new InstantiateUsingDefaultConstructorConverter(),
          new InstantiateUsingCreatorConverter(),
          new ExpectTokenConverter(),
          new DeserializeStandardValueConverter(),
          new InstanceConverter<>()
        )
      );
    try {
      Graphviz
        .fromGraph(graphGenerator.generateGraph(finalStep))
        .height(500)
        .render(GRAPH_FORMAT)
        .toFile(new File("target/graph" + (++i) + "." + GRAPH_FORMAT.fileExtension));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void removePreviousGraphs() {
    final File[] files =
      new File("target")
        .listFiles(s -> s.getName().matches("graph.*\\." + GRAPH_FORMAT.fileExtension));
    //noinspection ResultOfMethodCallIgnored
    Arrays.stream(requireNonNull(files)).forEach(File::delete);
  }

}
