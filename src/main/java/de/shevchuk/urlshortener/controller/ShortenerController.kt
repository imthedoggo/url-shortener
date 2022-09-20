package de.shevchuk.urlshortener.controller

import de.shevchuk.urlshortener.exception.UrlNotFoundException
import de.shevchuk.urlshortener.model.ResponseUrlDto
import de.shevchuk.urlshortener.model.UrlDto
import de.shevchuk.urlshortener.service.ShortenerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/v1/urls")
class ShortenerController(@Autowired val shortenerService: ShortenerService) {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createShortUrl(@RequestBody urlDto: UrlDto): ResponseUrlDto {
        return shortenerService.createShortUrl(urlDto.url)
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @kotlin.Throws(
        UrlNotFoundException::class
    )
    fun redirect(@PathVariable("id") id: String, response: HttpServletResponse) {
        val url = shortenerService.getAndValidateUrl(id)
        response.sendRedirect(url)
    }

    @get:GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    val allShortUrls: List<ResponseUrlDto>
        get() = shortenerService.allUrls

    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(
        UrlNotFoundException::class
    )
    fun deleteShortUrl(@PathVariable("id") id: String) {
        shortenerService.deleteUrl(id)
    }
}