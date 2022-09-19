package de.shevchuk.urlshortener.unitest;

import de.shevchuk.urlshortener.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilsTest {

    @Test
    public void testGenerateRandomUrlId() {
        final String randomId = Utils.generateRandomUrlId();
        assertEquals(Utils.SHORT_URL_ID_SIZE, randomId.length());
    }
}