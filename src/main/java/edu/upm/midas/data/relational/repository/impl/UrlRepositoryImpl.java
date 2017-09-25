package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.disnetdb.Url;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.UrlRepository;

import java.util.List;

public class UrlRepositoryImpl extends AbstractDao<Integer, Url>
                                implements UrlRepository{
    @Override
    public Url findById(Integer urlId) {
        Url url = getByKey(urlId);
        return url;
    }

    @SuppressWarnings("unchecked")
    public List<Url> findAllQuery() {
        return (List<Url>) getEntityManager()
                .createNamedQuery("Url.findAll")
                .getResultList();
    }

    @Override
    public void persist(Url url) {
        super.persist(url);
    }

    @Override
    public boolean deleteById(Integer urlId) {
        return false;
    }

    @Override
    public void delete(Url url) {
        super.delete(url);
    }

    @Override
    public Url update(Url url) {
        return super.update(url);
    }

    @Override
    public int updateByIdQuery(Integer urlId) {
        return 0;
    }
}
