package de.shevchuk.urlshortener.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@Builder
@RedisHash("urls")
public class Url {

    @Id
    private String id;
    private String url;
}