package de.shevchuk.urlshortener.util

import org.apache.commons.lang3.RandomStringUtils

object Utils {

    const val SHORT_URL_ID_SIZE = 6

    fun generateRandomUrlId(): String {
        return RandomStringUtils.random(6, true, true)
    }
}