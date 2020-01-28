package edu.upm.midas.repository.jpa.impl;
import edu.upm.midas.model.jpa.PersonConsent;
import edu.upm.midas.model.jpa.PersonConsentPK;
import edu.upm.midas.repository.jpa.AbstractDao;
import edu.upm.midas.repository.jpa.PersonConsentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 28/01/2020.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className PersonConsentRepositoryImpl
 * @see
 */
@Repository("PersonConsentRepositoryDao")
public class PersonConsentRepositoryImpl extends AbstractDao<PersonConsentPK, PersonConsent> implements PersonConsentRepository {

    @SuppressWarnings("unchecked")
    @Override
    public PersonConsent findById(PersonConsentPK personConsentPK) {
        PersonConsent personConsent = getByKey(personConsentPK);
        return personConsent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] findByIdNative(String personId, int consentId) {
        return new Object[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public PersonConsent findByPersonId(String personId) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PersonConsent> findAllQuery() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(PersonConsent personConsent) {
        super.persist(personConsent);
    }

    @Override
    public int insertNative(String personId, int consentId) {
        return 0;
    }

    @Override
    public boolean deleteById(PersonConsentPK personConsentPK) {
        return false;
    }

    @Override
    public void delete(PersonConsent personConsent) {

    }

    @Override
    public PersonConsent update(PersonConsent personConsent) {
        return super.update(personConsent);
    }

    @Override
    public int updateEnabledNative(String personId, int consentId) {
        return 0;
    }

    @Override
    public int updateByIdQuery(PersonConsent personConsent) {
        return 0;
    }
}
