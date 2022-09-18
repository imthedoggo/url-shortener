package de.shevchuk.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.URL;

@Getter
@Builder
@Jacksonized
@AllArgsConstructor
public class UrlDto {

    @URL
    private String url;
}
