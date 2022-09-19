package de.shevchuk.urlshortener.repository;

import de.shevchuk.urlshortener.entity.Url;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, String> {
    //overridden otherwise the default return Iterable
    List<Url> findAll();
}