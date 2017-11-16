package com.shawn.sys.util;

import java.math.BigDecimal;

/**
 * @author TaiL
 * @since 2013-4-7
 */
public final class G {

	private G() {
		throw new Error();
	}

	/**
	 * @param value
	 * @return string value, default is empty[""]
	 */
	public static String str(final Object value) {
		if (value != null) {
			try {
				return value.toString().trim();
			} catch (final Exception x) {
				x.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * @param value
	 * @return boolean value, default is false<br>
	 *         value == 1 ? true : false;
	 */
	public static boolean z(final Object value) {
		return G.z(value, false);
	}

	/**
	 * @param value
	 * @param defaultValue
	 * @return boolean value <br>
	 *         value == 1 ? true : false;
	 */
	public static boolean z(final Object value, final boolean defaultValue) {
		if (value != null) {
			if (value instanceof Boolean) {
				return (Boolean) value;
			}

			if (value.toString().equalsIgnoreCase("true")) {
				return true;
			}
			if (value.toString().equalsIgnoreCase("false")) {
				return false;
			}

			// 1: true, other: false
			return G.i(value, 0) == 1;
		}
		return defaultValue;
	}

	/**
	 * @param value
	 * @return int value, default is zero<br>
	 *         true ? 1 : 0;
	 */
	public static int i(final Object value) {
		return G.i(value, 0);
	}

	/**
	 * 
	 * @param value
	 * @param defaultValue
	 * @return int value <br>
	 *         true ? 1 : 0;
	 */
	public static int i(final Object value, final int defaultValue) {
		if (value != null) {
			if (value instanceof Integer) {
				return (Integer) value;
			}

			if (value instanceof Number) {
				return ((Number) value).intValue();
			}

			try {
				return Integer.parseInt(value.toString());
			} catch (final Exception x) {
				x.printStackTrace();
				if (value instanceof Boolean) {
					return (Boolean) value ? 1 : 0;
				}
			}
		}
		return defaultValue;
	}

	/**
	 * @param value
	 * @return long value, default is zero
	 */
	public static long l(final Object value) {
		return G.l(value, 0L);
	}

	/**
	 * @param value
	 * @param defaultValue
	 * @return long value
	 */
	public static long l(final Object value, final long defaultValue) {
		if (value != null && !G.str(value).isEmpty()) {
			if (value instanceof Long) {
				return (Long) value;
			}

			if (value instanceof Number) {
				return ((Number) value).longValue();
			}

			try {
				return Long.parseLong(value.toString());
			} catch (final Exception x) {
				x.printStackTrace();
			}
		}
		return defaultValue;
	}

	public static BigDecimal bd(final Object value) {
		return bd(value, BigDecimal.ZERO, 0);
	}

	public static BigDecimal bd(final Object value, final int scale) {
		return bd(value, BigDecimal.ZERO, scale);
	}

	public static BigDecimal bd(final Object value, final BigDecimal defaultValue, final int scale) {
		if (value != null) {
			if (value instanceof BigDecimal) {
				return ((BigDecimal) value).setScale(scale);
			}

			if (value instanceof Number) {
				return BigDecimal.valueOf(((Number) value).doubleValue()).setScale(scale);
			}

			try {
				return new BigDecimal(value.toString()).setScale(scale);
			} catch (final Exception x) {
				x.printStackTrace();
			}
		}
		return defaultValue.setScale(scale);
	}

}
