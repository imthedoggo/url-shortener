package de.shevchuk.urlshortener.controller;

import de.shevchuk.urlshortener.exception.UrlNotFoundException;
import de.shevchuk.urlshortener.model.ResponseUrlDto;
import de.shevchuk.urlshortener.model.UrlDto;
import de.shevchuk.urlshortener.service.ShortenerService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/urls")
public class ShortenerController {

    private final ShortenerService shortenerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUrlDto createShortUrl(@Valid @RequestBody UrlDto urlDto) {
        return shortenerService.createShortUrl(urlDto.getUrl());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void redirect(@PathVariable("id") String id, HttpServletResponse response) throws IOException, UrlNotFoundException {
        final ResponseUrlDto urlDto = shortenerService.getUrl(id);
        response.sendRedirect(urlDto.getUrl());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseUrlDto> getAllShortUrls() {
        return shortenerService.getAllUrls();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteShortUrl(@PathVariable("id") String id) throws UrlNotFoundException {
        shortenerService.deleteUrl(id);
    }
}