package com.plcarmel.steps.jackson;

import com.plcarmel.steps.jackson.classes.*;
import com.plcarmel.steps.jackson.graphviz.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FeatureTest {

  @Test
  public void emptyObjectTest() throws IOException {
    final String str = "{}";
    final Object result = new ObjectMapper().readValue(str, Object.class);
    assertNotNull(result);
    assertSame(result.getClass(), Object.class);
  }

  @Test
  public void customClassTest() throws IOException {
    final String str = "{}";
    final CustomClass result = new ObjectMapper().readValue(str, CustomClass.class);
    assertNotNull(result);
    assertSame(result.getClass(), CustomClass.class);
  }

  @Test
  public void fieldPublicStandardPropertyTest() throws IOException {
    final String str = "{ \"x\": 1234 }";
    final ClassWithPublicFieldStandardProperty result =
      new ObjectMapper().readValue(str, ClassWithPublicFieldStandardProperty.class);
    assertNotNull(result);
    assertEquals(1234, result.x);
  }

  @Test
  public void fieldTwoPublicStandardPropertiesTest() throws IOException {
    GraphRenderer.removePreviousGraphs();
    final GraphRenderer renderer = new GraphRenderer();
    final String str = "{ \"hello\": 1234, \"world\": -1 }";
    final ClassWithTwoPublicFieldStandardProperties result =
      new ObjectMapper().readValue(str, ClassWithTwoPublicFieldStandardProperties.class, renderer::printGraph);
    assertNotNull(result);
    assertEquals(1234, result.hello);
    assertEquals(-1, result.world);
  }

  @Test
  public void fieldPublicNonStandardPropertyTest() throws IOException {
    final String str = "{ \"x\": { \"x\": 1234 } }";
    final ClassWithPublicFieldNonStandardProperty result =
      new ObjectMapper().readValue(str, ClassWithPublicFieldNonStandardProperty.class);
    assertNotNull(result);
    assertEquals(1234, result.x.x);
  }

  @Test
  public void unwrappedPublicFieldPropertyTest() throws IOException {
    final String str = "{ \"x\": 1234 }";
    final ClassWithUnwrappedPublicFieldProperty result =
      new ObjectMapper().readValue(str, ClassWithUnwrappedPublicFieldProperty.class);
    assertNotNull(result);
    assertEquals(result.y.x, 1234);
  }

  @Test
  public void constructorPropertyTest() throws IOException {
    final String str = "{ \"z\": 1234 }";
    final ClassWithConstructorProperty result =
      new ObjectMapper().readValue(str, ClassWithConstructorProperty.class);
    assertNotNull(result);
    assertEquals(result.w, 1234);
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

  @Test
  public void constructorUnwrappedPropertyTest() throws IOException {
    final String str = "{ \"x\": 1234 }";
    final ClassWithUnwrappedConstructorProperty result =
      new ObjectMapper().readValue(str, ClassWithUnwrappedConstructorProperty.class);
    assertNotNull(result);
    assertNotNull(result.hello);
    assertEquals(result.hello.x,1234);
  }

}
