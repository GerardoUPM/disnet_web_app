package edu.upm.midas.repository.jpa.impl;

import edu.upm.midas.model.jpa.PersonToken;
import edu.upm.midas.model.jpa.PersonTokenPK;
import edu.upm.midas.repository.jpa.AbstractDao;
import edu.upm.midas.repository.jpa.PersonTokenRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
@Repository("PersonTokenRepositoryDao")
public class PersonTokenRepositoryImpl extends AbstractDao<PersonTokenPK, PersonToken>
                                        implements PersonTokenRepository {
    @Override
    public PersonToken findById(PersonTokenPK personTokenPK) {
        PersonToken personToken = getByKey(personTokenPK);
        return personToken;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PersonToken findByPersonId(String personId) {
        PersonToken personToken = null;
        List<PersonToken> emailConfirmationList = (List<PersonToken>) getEntityManager()
                .createNamedQuery("PersonToken.findByPersonId")
                .setParameter("personId", personId)
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(emailConfirmationList))
            personToken = emailConfirmationList.get(0);

        return personToken;
    }

    @SuppressWarnings("unchecked")
    public Object[] findByIdNative(String personId, String token) {
        Object[] oEmailConfirmation = null;
        List<Object[]> emailConfirmationList = (List<Object[]>) getEntityManager()
                .createNamedQuery("PersonToken.findByIdNative")
                .setParameter("personId", personId)
                .setParameter("token", token)
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(emailConfirmationList))
            oEmailConfirmation = emailConfirmationList.get(0);

        return oEmailConfirmation;
    }

    @SuppressWarnings("unchecked")
    public List<PersonToken> findAllQuery() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(PersonToken personToken) {
        super.persist(personToken);
    }

    @Override
    public int insertNative(String personId, String token, boolean enabled, Date date) {
        return getEntityManager()
                .createNamedQuery("PersonToken.insertNative")
                .setParameter("personId", personId)
                .setParameter("token", token)
                .setParameter("enabled", enabled)
                .setParameter("date", date)
                .executeUpdate();
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
        return super.update(personToken);
    }

    @Override
    public int updateEnabledNative(String personId, String token, boolean enabled, Date date, Date datetime) {
        return getEntityManager()
                .createNamedQuery("PersonToken.updateEnabledNative")
                .setParameter("personId", personId)
                .setParameter("token", token)
                .setParameter("enabled", enabled)
                .setParameter("date", date)
                .setParameter("datetime", datetime)
                .executeUpdate();
    }

    @Override
    public int updateByIdQuery(PersonToken personToken) {
        return 0;
    }
}
