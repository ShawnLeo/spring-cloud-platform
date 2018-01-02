package com.shawn.sys.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author TaiL
 */
public abstract class UuidHelper {

  private UuidHelper() {
  }

  public static String randomShorterString() {
    return toShorterString(UUID.randomUUID());
  }

  public static String namedShorterString(String name) {
    return toShorterString(namedUuid(name));
  }

  public static UUID namedUuid(String name) {
    return UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
  }

  public static String toShorterString(UUID uuid) {
    return new BigInteger(toPrettyString(uuid), 16).toString(Character.MAX_RADIX);
  }

  public static UUID parseShorterString(String uuid) {
    return parsePrettyString(new BigInteger(uuid, Character.MAX_RADIX).toString(16));
  }

  private static String toPrettyString(final UUID uuid) {
    return uuid.toString().replace("-", "");
  }

  private static UUID parsePrettyString(final String uuid) {
    final StringBuilder builder = new StringBuilder();
    try {
      builder.append(uuid.subSequence(0, 8)).append("-");
      builder.append(uuid.subSequence(8, 12)).append("-");
      builder.append(uuid.subSequence(12, 16)).append("-");
      builder.append(uuid.subSequence(16, 20)).append("-");
      builder.append(uuid.subSequence(20, 32)).append("-");
      return UUID.fromString(builder.toString());
    } catch (final Exception x) {
      throw new IllegalArgumentException("无效的UUID字符串[" + uuid + "]", x);
    }
  }

}
