package de.shevchuk.urlshortener.service

import de.shevchuk.urlshortener.repository.UrlRepository
import de.shevchuk.urlshortener.entity.Url
import de.shevchuk.urlshortener.exception.UrlNotFoundException
import de.shevchuk.urlshortener.model.ResponseUrlDto
import de.shevchuk.urlshortener.util.Utils
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
@AllArgsConstructor
class ShortenerService(@Autowired val urlRepository: UrlRepository) {

    fun createShortUrl(longUrl: String): ResponseUrlDto {
        val id: String = Utils.generateRandomUrlId()
        urlRepository.save(
            Url(id, longUrl)
        )
        return buildResponseUrlDto(id, longUrl)
    }

    @Throws(UrlNotFoundException::class)
    fun getAndValidateUrl(id: String): String {
        val url: Optional<Url> = urlRepository.findById(id)
        if (url.isEmpty) {
            throw UrlNotFoundException(String.format("The URL-ID %s you're trying to get does not exist.", id))
        }
        return url.get().url
    }

    val allUrls: List<ResponseUrlDto>
        get() = urlRepository.findAll()
            .map { u -> buildResponseUrlDto(u.id, u.url) }

    @Throws(UrlNotFoundException::class)
    fun deleteUrl(id: String) {
        getAndValidateUrl(id)
        urlRepository.deleteById(id)
    }

    companion object {
        private fun buildResponseUrlDto(id: String, longUrl: String): ResponseUrlDto {
            return ResponseUrlDto(id, longUrl)
        }
    }
}