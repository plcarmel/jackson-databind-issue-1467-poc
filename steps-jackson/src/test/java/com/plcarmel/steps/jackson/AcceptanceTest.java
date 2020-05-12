package com.plcarmel.steps.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonParser;
import com.plcarmel.steps.graphviz.SimpleGraphGenerator;
import com.plcarmel.steps.theory.StepInstance;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.*;

public class AcceptanceTest {

  @Test
  public void emptyObjectTest() throws IOException {
    final String str = "{}";
    final Object result = new ObjectMapper().readValue(str, Object.class);
    assertNotNull(result);
    assertSame(result.getClass(), Object.class);
  }

  public static class CustomClass { }

  @Test
  public void customClassTest() throws IOException {
    final String str = "{}";
    final CustomClass result = new ObjectMapper().readValue(str, CustomClass.class);
    assertNotNull(result);
    assertSame(result.getClass(), CustomClass.class);
  }

  public static class ClassWithPublicFieldStandardProperty {
    public int x;
  }

  @Test
  public void fieldPublicStandardPropertyTest() throws IOException {
    final String str = "{ \"x\": 1234 }";
    final ClassWithPublicFieldStandardProperty result =
      new ObjectMapper().readValue(str, ClassWithPublicFieldStandardProperty.class);
    assertNotNull(result);
    assertEquals(1234, result.x);
  }

  public static class ClassWithPublicFieldNonStandardProperty {
    public ClassWithPublicFieldStandardProperty x;
  }

  @Test
  public void fieldPublicNonStandardPropertyTest() throws IOException {
    final String str = "{ \"x\": { \"x\": 1234 } }";
    final ClassWithPublicFieldNonStandardProperty result =
      new ObjectMapper().readValue(str, ClassWithPublicFieldNonStandardProperty.class);
    assertNotNull(result);
    assertEquals(1234, result.x.x);
  }

  public static class ClassWithUnwrappedPublicFieldProperty {
    @JsonUnwrapped
    public ClassWithPublicFieldStandardProperty y;
  }

  @Test
  public void unwrappedPublicFieldPropertyTest() throws IOException {
    final String str = "{ \"x\": 1234 }";
    final ClassWithUnwrappedPublicFieldProperty result =
      new ObjectMapper().readValue(str, ClassWithUnwrappedPublicFieldProperty.class);
    assertNotNull(result);
    assertEquals(result.y.x, 1234);
  }

  public static class ClassWithConstructorProperty {
    public final int w;
    @JsonCreator
    public ClassWithConstructorProperty(@JsonProperty("z") int x) {
      w = x;
    }
  }

  @Test
  public void constructorPropertyTest() throws IOException {
    final String str = "{ \"z\": 1234 }";
    final ClassWithConstructorProperty result =
      new ObjectMapper().readValue(str, ClassWithConstructorProperty.class);
    assertNotNull(result);
    assertEquals(result.w, 1234);
  }

  public static class ClassWithNonStandardConstructorProperty {
    public final ClassWithPublicFieldStandardProperty hello;
    public ClassWithNonStandardConstructorProperty(@JsonProperty("world") ClassWithPublicFieldStandardProperty hello) {
      this.hello = hello;
    }
  }

  private int i = 0;

  private final static Format graphFormat = Format.SVG;

  private void printGraph(StepInstance<JsonParser, ?> finalStep) {
    final SimpleGraphGenerator<StepInstance<JsonParser, ?>> graphGenerator = new SimpleGraphGenerator<>();
    try {
      Graphviz
        .fromGraph(graphGenerator.generateGraph(finalStep))
        .height(500)
        .render(graphFormat)
        .toFile(new File("target/graph" + (++i) + "." + graphFormat.fileExtension));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void removePreviousGraphs() {
    final File[] files =
        new File("target")
          .listFiles(s -> s.getName().matches("graph.*\\." + graphFormat.fileExtension));
    //noinspection ResultOfMethodCallIgnored
    Arrays.stream(requireNonNull(files)).forEach(File::delete);
  }

  @Test
  public void constructorNonStandardPropertyTest() throws IOException {
    final String str = "{ \"world\": { \"x\": 1234 } }";
    removePreviousGraphs();
    final ClassWithNonStandardConstructorProperty result =
      new ObjectMapper().readValue(str, ClassWithNonStandardConstructorProperty.class, this::printGraph);
    assertNotNull(result);
    assertNotNull(result.hello);
    assertEquals(result.hello.x,1234);
  }


}
