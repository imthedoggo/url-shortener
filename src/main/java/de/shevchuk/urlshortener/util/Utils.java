package de.shevchuk.urlshortener.util;

import io.seruco.encoding.base62.Base62;
import org.apache.commons.lang3.RandomStringUtils;

public final class Utils {

    @Deprecated //experimented with base62 encoding
    public static String encodeIntoBase62(String encodedString) {
        Base62 base62 = Base62.createInstance();
        final byte[] encoded = base62.encode(encodedString.getBytes());
        return new String(encoded);
    }

    public static String generateRandomUrlId() {
        return RandomStringUtils.random(6, true, true);
    }
}
