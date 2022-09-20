package de.shevchuk.urlshortener.unitest

import de.shevchuk.urlshortener.util.Utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UtilsTest {
    @Test
    fun testGenerateRandomUrlId() {
        val randomId: String = Utils.generateRandomUrlId()
        assertEquals(Utils.SHORT_URL_ID_SIZE, randomId.length)
    }
}