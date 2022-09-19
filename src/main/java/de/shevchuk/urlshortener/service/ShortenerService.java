package de.shevchuk.urlshortener.service;

import de.shevchuk.urlshortener.entity.Url;
import de.shevchuk.urlshortener.exception.UrlNotFoundException;
import de.shevchuk.urlshortener.model.ResponseUrlDto;
import de.shevchuk.urlshortener.repository.UrlRepository;
import de.shevchuk.urlshortener.util.Utils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShortenerService {

    public UrlRepository urlRepository;

    public ResponseUrlDto createShortUrl(String longUrl) {
        String id = Utils.generateRandomUrlId();
        urlRepository.save(Url.builder()
            .id(id)
            .url(longUrl)
            .build());
        return buildResponseUrlDto(id, longUrl);
    }

    public String getAndValidateUrl(String id) throws UrlNotFoundException {
        final Optional<Url> url = urlRepository.findById(id);
        if (url.isEmpty()) {
            throw new UrlNotFoundException(String.format("The URL-ID %s you're trying to get does not exist.", id));
        }
        return url.get().getUrl();
    }

    public List<ResponseUrlDto> getAllUrls() {
        return urlRepository.findAll().stream()
            .map(u -> buildResponseUrlDto(u.getId(), u.getUrl()))
            .collect(Collectors.toList());
    }

    public void deleteUrl(String id) throws UrlNotFoundException {
        getAndValidateUrl(id);
        urlRepository.deleteById(id);
    }

    private static ResponseUrlDto buildResponseUrlDto(String id, String longUrl) {
        return ResponseUrlDto.builder()
            .id(id)
            .url(longUrl)
            .build();
    }
}