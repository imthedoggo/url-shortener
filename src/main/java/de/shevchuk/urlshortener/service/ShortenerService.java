package de.shevchuk.urlshortener.service;

import de.shevchuk.urlshortener.exception.UrlNotFoundException;
import de.shevchuk.urlshortener.model.ResponseUrlDto;
import de.shevchuk.urlshortener.util.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortenerService {
    public Map<String, String> urlMap = new HashMap<>() {};

    public ResponseUrlDto createShortUrl(String longUrl) {
        String id = Utils.generateRandomUrlId();
        urlMap.put(id, longUrl);
        return buildResponseUrlDto(longUrl, id);
    }

    private static ResponseUrlDto buildResponseUrlDto(String longUrl, String id) {
        return ResponseUrlDto.builder()
            .id(id)
            .url(longUrl)
            .build();
    }

    public ResponseUrlDto getUrl(String id) throws UrlNotFoundException {
        final String longUrl = getAndValidateUrl(id);
        return buildResponseUrlDto(longUrl, id);
    }

    public String getAndValidateUrl(String id) throws UrlNotFoundException {
        final String url = urlMap.get(id);
        if (url == null) {
            throw new UrlNotFoundException(String.format("The URL-ID %s you're trying to get does not exist.", id));
        }
        return url;
    }

    public void deleteUrl(String id) throws UrlNotFoundException {
        getAndValidateUrl(id);
        urlMap.remove(id);
    }

    public List<ResponseUrlDto> getAllUrls() {
        return urlMap.entrySet().stream()
            .map(url -> buildResponseUrlDto(url.getValue(), url.getKey()))
            .collect(Collectors.toList());
    }
}