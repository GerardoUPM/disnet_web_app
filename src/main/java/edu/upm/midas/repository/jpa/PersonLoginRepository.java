package edu.upm.midas.repository.jpa;


import edu.upm.midas.model.jpa.PersonLogin;
import edu.upm.midas.model.jpa.PersonLoginPK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public interface PersonLoginRepository {

    PersonLogin findById(PersonLoginPK personLoginPK);

    Object[] findByIdNative(String personId, String loginId);

    PersonLogin findByPersonId(String personId);

    List<PersonLogin> findAllQuery();

    List<Object[]> findAllNative();

    void persist(PersonLogin personLogin);

//    int insertNative(String personId, String loginId, boolean enabled, Date date);

    boolean deleteById(PersonLoginPK personLoginPK);

    void delete(PersonLogin personLogin);

    PersonLogin update(PersonLogin personLogin);

    int updateEnabledNative(String personId, String loginId, boolean enabled, Date date, Date datetime);

    int updateByIdQuery(PersonLogin personLogin);

}
