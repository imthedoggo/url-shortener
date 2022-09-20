package de.shevchuk.urlshortener.model

import org.springframework.lang.NonNull

data class UrlDto(
    @NonNull
    var url: String
)

