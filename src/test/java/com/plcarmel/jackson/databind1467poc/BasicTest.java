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

  public static class ClassWithPublicProperty {
    public int x;
  }

  @Test
  public void fieldPropertyTest() throws IOException {
    final String str = "{ \"x\": 3 }";
    final ClassWithPublicProperty result = new ObjectMapper().readValue(str, ClassWithPublicProperty.class);
    assertNotNull(result);
    assertEquals(3, result.x);
  }

}
