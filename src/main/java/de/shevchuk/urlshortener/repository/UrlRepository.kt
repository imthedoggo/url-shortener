package de.shevchuk.urlshortener.repository

import de.shevchuk.urlshortener.entity.Url
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : CrudRepository<Url, String> {
    //overridden otherwise the default return Iterable
    override fun findAll(): List<Url>
}