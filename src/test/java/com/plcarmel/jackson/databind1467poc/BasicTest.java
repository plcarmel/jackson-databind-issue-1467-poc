package com.plcarmel.jackson.databind1467poc;

import com.plcarmel.jackson.databind1467poc.example.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class BasicTest {

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

}
