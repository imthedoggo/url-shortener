package de.shevchuk.urlshortener.integrationtest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import de.shevchuk.urlshortener.model.UrlDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortenerControllerTest {

    public static final String LONG_URL_1 = "http://thisIsLongUrl1.com";
    public static final String LONG_URL_2 = "http://thisIsLongUrl2.com";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testShortUrlIdCreation() throws Exception {
        UrlDto longUrl = buildUrlDto(LONG_URL_1);
        createAndAssertShortUrlId(longUrl);
    }

    @Test
    public void testGetUrlById() throws Exception {
        UrlDto longUrl2 = buildUrlDto(LONG_URL_2);
        final String urlId = createAndAssertShortUrlId(longUrl2);

        mvc.perform(get("/v1/short-urls/" + urlId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("url", is(LONG_URL_2)))
            .andReturn();
    }

    @Test
    public void testUrlDeletion() throws Exception {
        UrlDto longUrl1 = buildUrlDto(LONG_URL_1);
        String urlId = createAndAssertShortUrlId(longUrl1);

        //delete
        mvc.perform(delete("/v1/short-urls/" + urlId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        //check that url doesn't exist more
        mvc.perform(get("/v1/short-urls/" + urlId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    private static UrlDto buildUrlDto(String longUrl) {
        return UrlDto.builder()
            .url(longUrl)
            .build();
    }

    private String createAndAssertShortUrlId(UrlDto longUrl) throws Exception {
        final MvcResult createUrlResult = mvc.perform(post("/v1/short-urls")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(longUrl)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("url").isNotEmpty())
            .andReturn();
        return JsonPath.read(createUrlResult.getResponse().getContentAsString(), "$.id");
    }
}
