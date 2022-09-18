package de.shevchuk.urlshortener.util;

import io.seruco.encoding.base62.Base62;

public final class Utils {

    public static String encodeIntoBase62(String encodedString) {
        Base62 base62 = Base62.createInstance();
        final byte[] encoded = base62.encode(encodedString.getBytes());
        return new String(encoded);
    }
}
