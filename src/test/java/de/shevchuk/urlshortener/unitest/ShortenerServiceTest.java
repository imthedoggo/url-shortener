package de.shevchuk.urlshortener.unitest;

import de.shevchuk.urlshortener.entity.Url;
import de.shevchuk.urlshortener.exception.UrlNotFoundException;
import de.shevchuk.urlshortener.model.ResponseUrlDto;
import de.shevchuk.urlshortener.repository.UrlRepository;
import de.shevchuk.urlshortener.service.ShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static de.shevchuk.urlshortener.TestConstants.LONG_URL_1;
import static de.shevchuk.urlshortener.TestConstants.LONG_URL_2;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShortenerServiceTest {

    @Autowired
    private ShortenerService shortenerService;

    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    public void resetDb() {
        urlRepository.deleteAll();
    }

    @Test
    void testShortUrlIdCreation() {
        ResponseUrlDto url = createAndSaveUrl(LONG_URL_1);
        Optional<Url> savedUrl = urlRepository.findById(url.getId());

        assertTrue(savedUrl.isPresent());
        assertEquals(url.getId(), savedUrl.get().getId());
        assertEquals(url.getUrl(), savedUrl.get().getUrl());
    }

    @Test
    void testGetAndValidateUrlById() throws UrlNotFoundException {
        ResponseUrlDto url = createAndSaveUrl(LONG_URL_1);
        String fetchedUrl = shortenerService.getAndValidateUrl(url.getId());

        assertEquals(url.getUrl(), fetchedUrl);
    }

    @Test
    void testGetAllUrls() {
        ResponseUrlDto url1 = createAndSaveUrl(LONG_URL_1);
        ResponseUrlDto url2 = createAndSaveUrl(LONG_URL_2);

        List<ResponseUrlDto> urls = shortenerService.getAllUrls();
        assertEquals(urls.size(), 2);
        assertEquals(urls.get(0).getId(), url1.getId());
        assertEquals(urls.get(0).getUrl(), url1.getUrl());
        assertEquals(urls.get(1).getId(), url2.getId());
        assertEquals(urls.get(1).getUrl(), url2.getUrl());
    }

    @Test
    void testUrlDeletion() throws UrlNotFoundException {
        ResponseUrlDto url = createAndSaveUrl(LONG_URL_1);
        shortenerService.deleteUrl(url.getId());

        Optional<Url> savedUrl = urlRepository.findById(url.getId());
        assertFalse(savedUrl.isPresent());
    }

    @Test
    void testGetAndValidateThrowUrlNotFound() {
        assertThrows(UrlNotFoundException.class, () -> shortenerService.getAndValidateUrl("NON_EXISTENT"));
    }

    @Test
    void testDeleteThrowUrlNotFound() {
        assertThrows(UrlNotFoundException.class, () -> shortenerService.deleteUrl("NON_EXISTENT"));
    }

    private ResponseUrlDto createAndSaveUrl(String longUrl) {
        return shortenerService.createShortUrl(longUrl);
    }
}