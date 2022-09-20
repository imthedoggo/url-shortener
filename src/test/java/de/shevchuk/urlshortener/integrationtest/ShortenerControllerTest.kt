package de.shevchuk.urlshortener.integrationtest

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import de.shevchuk.urlshortener.TestConstants.LONG_URL_1
import de.shevchuk.urlshortener.TestConstants.LONG_URL_2
import de.shevchuk.urlshortener.model.UrlDto
import de.shevchuk.urlshortener.repository.UrlRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class ShortenerControllerTest(
    @Autowired val mvc: MockMvc, @Autowired val objectMapper: ObjectMapper, @Autowired val urlRepository: UrlRepository) {

    @BeforeEach
    fun resetDb() {
        urlRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun testShortUrlIdCreation() {
        val longUrl: UrlDto = buildUrlDto(LONG_URL_1)
        createAndAssertShortUrlId(longUrl)
    }

    @Test
    @Throws(Exception::class)
    fun testRedirectUrlById() {
        val longUrl2: UrlDto = buildUrlDto(LONG_URL_2)
        val urlId = createAndAssertShortUrlId(longUrl2)
        mvc.perform(
            MockMvcRequestBuilders.get("/v1/urls/$urlId")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection)
            .andReturn()
    }

    @Test
    @Throws(Exception::class)
    fun testUrlDeletion() {
        val longUrl1: UrlDto = buildUrlDto(LONG_URL_1)
        val urlId = createAndAssertShortUrlId(longUrl1)

        //delete
        mvc.perform(
            MockMvcRequestBuilders.delete("/v1/urls/$urlId")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        //check that url doesn't exist more
        mvc.perform(
            MockMvcRequestBuilders.get("/v1/urls/$urlId")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Throws(Exception::class)
    private fun createAndAssertShortUrlId(longUrl: UrlDto): String {
        val createUrlResult = mvc.perform(
            MockMvcRequestBuilders.post("/v1/urls")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(longUrl))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("url").isNotEmpty)
            .andReturn()
        return JsonPath.read(createUrlResult.response.contentAsString, "$.id")
    }

    companion object {
        private fun buildUrlDto(longUrl: String): UrlDto {
            return UrlDto(longUrl)
        }
    }
}