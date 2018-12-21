package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.disnetdb.PersonLogin;
import edu.upm.midas.data.relational.entities.disnetdb.PersonLoginPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.PersonLoginRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
@Repository("PersonLoginRepositoryDao")
public class PersonLoginRepositoryImpl extends AbstractDao<PersonLoginPK, PersonLogin>
                                        implements PersonLoginRepository {
    @Override
    public PersonLogin findById(PersonLoginPK personLoginPK) {
        PersonLogin personLogin = getByKey(personLoginPK);
        return personLogin;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PersonLogin findByPersonId(String personId) {
        PersonLogin personLogin = null;
//        List<PersonLogin> emailConfirmationList = (List<PersonLogin>) getEntityManager()
//                .createNamedQuery("PersonLogin.findByPersonId")
//                .setParameter("personId", personId)
//                .setMaxResults(1)
//                .getResultList();
//        if (CollectionUtils.isNotEmpty(emailConfirmationList))
//            personLogin = emailConfirmationList.get(0);

        return personLogin;
    }

    @SuppressWarnings("unchecked")
    public Object[] findByIdNative(String personId, String token) {
        Object[] oEmailConfirmation = null;
//        List<Object[]> emailConfirmationList = (List<Object[]>) getEntityManager()
//                .createNamedQuery("PersonLogin.findByIdNative")
//                .setParameter("personId", personId)
//                .setParameter("token", token)
//                .setMaxResults(1)
//                .getResultList();
//        if (CollectionUtils.isNotEmpty(emailConfirmationList))
//            oEmailConfirmation = emailConfirmationList.get(0);

        return oEmailConfirmation;
    }

    @SuppressWarnings("unchecked")
    public List<PersonLogin> findAllQuery() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(PersonLogin personLogin) {
        super.persist(personLogin);
    }

//    @Override
//    public int insertNative(String personId, String token, boolean enabled, Date date) {
//        return getEntityManager()
//                .createNamedQuery("PersonLogin.insertNative")
//                .setParameter("personId", personId)
//                .setParameter("token", token)
//                .setParameter("enabled", enabled)
//                .setParameter("date", date)
//                .executeUpdate();
//    }

    @Override
    public boolean deleteById(PersonLoginPK personLoginPK) {
        return false;
    }

    @Override
    public void delete(PersonLogin personLogin) {

    }

    @Override
    public PersonLogin update(PersonLogin personLogin) {
        return super.update(personLogin);
    }

    @Override
    public int updateEnabledNative(String personId, String token, boolean enabled, Date date, Date datetime) {
        return 0;
//        return getEntityManager()
//                .createNamedQuery("PersonLogin.updateEnabledNative")
//                .setParameter("personId", personId)
//                .setParameter("token", token)
//                .setParameter("enabled", enabled)
//                .setParameter("date", date)
//                .setParameter("datetime", datetime)
//                .executeUpdate();
    }

    @Override
    public int updateByIdQuery(PersonLogin personLogin) {
        return 0;
    }
}
