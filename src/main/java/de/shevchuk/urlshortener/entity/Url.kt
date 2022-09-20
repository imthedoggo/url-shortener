package de.shevchuk.urlshortener.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("urls")
data class Url (
    @Id
    var id: String,
    var url: String
)