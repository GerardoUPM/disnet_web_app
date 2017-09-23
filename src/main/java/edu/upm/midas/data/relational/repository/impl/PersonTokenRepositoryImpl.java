package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.disnetdb.PersonToken;
import edu.upm.midas.data.relational.entities.disnetdb.PersonTokenPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.PersonTokenRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public class PersonTokenRepositoryImpl extends AbstractDao<PersonTokenPK, PersonToken>
                                        implements PersonTokenRepository {
    @Override
    public PersonToken findById(PersonTokenPK personTokenPK) {
        return null;
    }

    @Override
    public Object[] findByIdNative(String personToken) {
        return new Object[0];
    }

    @Override
    public List<PersonToken> findAllQuery() {
        return null;
    }

    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(PersonToken personToken) {

    }

    @Override
    public int insertNative(String personId, String personToken, boolean enabled, Date date) {
        return 0;
    }

    @Override
    public boolean deleteById(PersonTokenPK personTokenPK) {
        return false;
    }

    @Override
    public void delete(PersonToken personToken) {

    }

    @Override
    public PersonToken update(PersonToken personToken) {
        return null;
    }

    @Override
    public int updateEnabled(String personToken) {
        return 0;
    }

    @Override
    public int updateByIdQuery(PersonToken personToken) {
        return 0;
    }
}
