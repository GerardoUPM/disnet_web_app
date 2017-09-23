package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.disnetdb.PersonToken;
import edu.upm.midas.data.relational.entities.disnetdb.PersonTokenPK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public interface PersonTokenRepository {

    PersonToken findById(PersonTokenPK personTokenPK);

    Object[] findByIdNative(String personToken);

    List<PersonToken> findAllQuery();

    List<Object[]> findAllNative();

    void persist(PersonToken personToken);

    int insertNative(String personId, String personToken, boolean enabled, Date date);

    boolean deleteById(PersonTokenPK personTokenPK);

    void delete(PersonToken personToken);

    PersonToken update(PersonToken personToken);

    int updateEnabled(String personToken);

    int updateByIdQuery(PersonToken personToken);

}
