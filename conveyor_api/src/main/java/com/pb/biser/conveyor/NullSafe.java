package com.pb.biser.conveyor;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

/**
 * @author tasman
 */
public final class NullSafe {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private NullSafe() {
    }

    public static Integer toInt(Long value) {
        return value == null ? null : value.intValue();
    }

    public static String toString(Long value) {
        return value == null ? null : String.valueOf(value);
    }

    public static byte[] toBytes(String value) {
        return value == null ? null : value.getBytes(Charset.forName(DEFAULT_ENCODING));
    }

    public static void argumentIsNotNull(Object value, String name) {
        if (value == null || StringUtils.isBlank(value.toString())) {
            throw new IllegalArgumentException(String.format("Argument %s must not be null", name));
        }
    }


}
