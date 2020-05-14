package com.plcarmel.steps.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.plcarmel.steps.jackson.graphviz.*;
import org.junit.Test;

import java.io.IOException;

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
    @JsonCreator public ClassWithNonStandardConstructorProperty(
      @JsonProperty("world") ClassWithPublicFieldStandardProperty hello
    ) {
      this.hello = hello;
    }
  }

  @Test
  public void constructorNonStandardPropertyTest() throws IOException {
    final String str = "{ \"world\": { \"x\": 1234 } }";
    final ClassWithNonStandardConstructorProperty result =
      new ObjectMapper().readValue(str, ClassWithNonStandardConstructorProperty.class);
    assertNotNull(result);
    assertNotNull(result.hello);
    assertEquals(result.hello.x,1234);
  }

  public static class ClassWithUnwrappedConstructorProperty {
    public final ClassWithPublicFieldStandardProperty hello;
    @JsonCreator public ClassWithUnwrappedConstructorProperty(
      @JsonUnwrapped @JsonProperty("world") ClassWithPublicFieldStandardProperty hello
    ) {
      this.hello = hello;
    }
  }

  @Test
  public void constructorUnwrappedPropertyTest() throws IOException {
    final String str = "{ \"x\": 1234 }";
    GraphRenderer.removePreviousGraphs();
    final GraphRenderer renderer = new GraphRenderer();
    final ClassWithUnwrappedConstructorProperty result =
      new ObjectMapper().readValue(str, ClassWithUnwrappedConstructorProperty.class, renderer::printGraph);
    assertNotNull(result);
    assertNotNull(result.hello);
    assertEquals(result.hello.x,1234);
  }

}
