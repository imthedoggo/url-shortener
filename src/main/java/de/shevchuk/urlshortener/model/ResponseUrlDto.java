package de.shevchuk.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@AllArgsConstructor
public class ResponseUrlDto {

    private String id;
    private String url;
}
