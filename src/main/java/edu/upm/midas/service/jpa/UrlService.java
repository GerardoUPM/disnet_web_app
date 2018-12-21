package edu.upm.midas.service.jpa;

import edu.upm.midas.model.jpa.Url;

import java.util.List;

public interface UrlService {

    Url findById(Integer urlId);

    Url findByUrl(String url);

    List<Url> findAllQuery();

    void save(Url url);

    boolean deleteById(Integer urlId);

    void delete(Url url);

    Url update(Url url);

    int updateByIdQuery(Integer urlId);

}
