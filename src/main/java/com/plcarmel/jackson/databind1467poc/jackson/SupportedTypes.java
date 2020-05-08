package com.plcarmel.jackson.databind1467poc.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;
import static java.util.stream.Collectors.*;

public class SupportedTypes {

  public final static Set<Class<?>> booleanClasses =
    Stream.of(
      boolean.class,
      Boolean.class
    ).collect(toSet());

  public final static Set<Class<?>> integerClasses =
    Stream.of(
      byte.class,
      short.class,
      int.class,
      long.class,
      Byte.class,
      Short.class,
      Integer.class,
      Long.class,
      BigInteger.class,
      Number.class
    ).collect(toSet());

  public final static Set<Class<?>> decimalClasses =
    Stream.of(
      float.class,
      double.class,
      Float.class,
      Double.class,
      BigDecimal.class,
      Number.class
    ).collect(toSet());

  public final static Set<Class<?>> stringClasses =
    Stream.of(
      String.class
    ).collect(toSet());

  public final static Set<Class<?>> collectionClasses =
    Stream.of(
      Collection.class,
      List.class,
      Set.class,
      ArrayList.class,
      LinkedList.class,
      HashSet.class,
      SortedSet.class
    ).collect(toSet());

  public final static Set<Class<?>> standardTypeClasses =
    Stream.of(
      booleanClasses,
      integerClasses,
      decimalClasses,
      stringClasses
    ).flatMap(Set::stream).collect(toSet());

  public final static Set<Class<?>> numberClasses =
    Stream.of(
      integerClasses,
      decimalClasses
    ).flatMap(Set::stream).collect(toSet());

  public final static Set<Class<?>> primitiveTypes =
    Stream.of(
      boolean.class,
      byte.class,
      short.class,
      int.class,
      long.class,
      float.class,
      double.class
    ).collect(toSet());

  public final static Map<Class<?>, Class<?>> unboxedToBoxed =
    Stream.of(
      entry(byte.class, Byte.class),
      entry(short.class, Short.class),
      entry(int.class, Integer.class),
      entry(long.class, Long.class),
      entry(float.class, Float.class),
      entry(double.class, Double.class)
    ).collect(toMap(SupportedTypes::key, SupportedTypes::value));;

  private static <K,V> Map.Entry<K,V> entry(K k, V v) {
    return new AbstractMap.SimpleImmutableEntry<>(k,v);
  }

  private static <K,V> K key(Map.Entry<K,V> e) {
    return e.getKey();
  }

  private static <K,V> V value(Map.Entry<K,V> e) {
    return e.getValue();
  }

  public final static Map<Class<?>, Function<JsonParser, ?>> typeToValueParser =
    Stream.of(
      entry(byte.class, sneaked(JsonParser::getByteValue)),
      entry(Byte.class, sneaked(JsonParser::getByteValue)),
      entry(short.class, sneaked(JsonParser::getShortValue)),
      entry(Short.class, sneaked(JsonParser::getShortValue)),
      entry(int.class, sneaked(JsonParser::getIntValue)),
      entry(Integer.class, sneaked(JsonParser::getIntValue)),
      entry(long.class, sneaked(JsonParser::getLongValue)),
      entry(Long.class, sneaked(JsonParser::getLongValue)),
      entry(float.class, sneaked(JsonParser::getFloatValue)),
      entry(Float.class, sneaked(JsonParser::getFloatValue)),
      entry(double.class, sneaked(JsonParser::getDoubleValue)),
      entry(Double.class, sneaked(JsonParser::getDoubleValue)),
      entry(BigInteger.class, sneaked(JsonParser::getBigIntegerValue)),
      entry(BigDecimal.class, sneaked(JsonParser::getDecimalValue))
    ).collect(toMap(SupportedTypes::key, SupportedTypes::value));

  public final static Map<JsonToken, Set<Class<?>>> tokenToSupportedTypes =
    Stream.of(
      entry(JsonToken.VALUE_NUMBER_FLOAT, numberClasses),
      entry(JsonToken.VALUE_NUMBER_INT, integerClasses),
      entry(JsonToken.VALUE_STRING, stringClasses),
      entry( JsonToken.VALUE_FALSE, booleanClasses),
      entry(JsonToken.VALUE_TRUE, booleanClasses)
    ).collect(toMap(SupportedTypes::key, SupportedTypes::value));
}
