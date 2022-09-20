package de.shevchuk.urlshortener.unitest

import de.shevchuk.urlshortener.TestConstants.LONG_URL_1
import de.shevchuk.urlshortener.TestConstants.LONG_URL_2
import de.shevchuk.urlshortener.entity.Url
import de.shevchuk.urlshortener.exception.UrlNotFoundException
import de.shevchuk.urlshortener.model.ResponseUrlDto
import de.shevchuk.urlshortener.repository.UrlRepository
import de.shevchuk.urlshortener.service.ShortenerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class ShortenerServiceTest(@Autowired val shortenerService: ShortenerService, @Autowired val urlRepository: UrlRepository){

    @BeforeEach
    fun resetDb() {
        urlRepository.deleteAll()
    }

    @Test
    fun testShortUrlIdCreation() {
        val url: ResponseUrlDto = createAndSaveUrl(LONG_URL_1)
        val savedUrl: Optional<Url> = urlRepository.findById(url.id)
        assertTrue(savedUrl.isPresent)
        assertEquals(url.id, savedUrl.get().id)
        assertEquals(url.url, savedUrl.get().url)
    }

    @Test
    @Throws(UrlNotFoundException::class)
    fun testGetAndValidateUrlById() {
        val url: ResponseUrlDto = createAndSaveUrl(LONG_URL_1)
        val fetchedUrl: String = shortenerService.getAndValidateUrl(url.id)
        assertEquals(url.url, fetchedUrl)
    }

    @Test
    fun testGetAllUrls() {
        val url1: ResponseUrlDto = createAndSaveUrl(LONG_URL_1)
        val url2: ResponseUrlDto = createAndSaveUrl(LONG_URL_2)
        val urls: List<ResponseUrlDto> = shortenerService.allUrls
        assertEquals(urls.size, 2)
        assertTrue(urls.contains(url1))
        assertTrue(urls.contains(url2))
    }

    @Test
    @Throws(UrlNotFoundException::class)
    fun testUrlDeletion() {
        val url: ResponseUrlDto = createAndSaveUrl(LONG_URL_1)
        shortenerService.deleteUrl(url.id)
        val savedUrl: Optional<Url> = urlRepository.findById(url.id)
        Assertions.assertFalse(savedUrl.isPresent)
    }

    @Test
    fun testGetAndValidateThrowUrlNotFound() {
        Assertions.assertThrows(UrlNotFoundException::class.java, Executable {
            shortenerService.getAndValidateUrl(
                "NON_EXISTENT"
            )
        })
    }

    @Test
    fun testDeleteThrowUrlNotFound() {
        Assertions.assertThrows(UrlNotFoundException::class.java, Executable {
            shortenerService.deleteUrl(
                "NON_EXISTENT"
            )
        })
    }

    private fun createAndSaveUrl(longUrl: String): ResponseUrlDto {
        return shortenerService.createShortUrl(longUrl)
    }
}