package edu.upm.midas.service.jpa.impl;
import edu.upm.midas.model.jpa.PersonConsent;
import edu.upm.midas.model.jpa.PersonConsentPK;
import edu.upm.midas.repository.jpa.PersonConsentRepository;
import edu.upm.midas.service.jpa.PersonConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 28/01/2020.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className PersonConsentServiceImpl
 * @see
 */
@Service("personConsentService")
public class PersonConsentServiceImpl implements PersonConsentService {

    @Autowired
    private PersonConsentRepository daoPersonConsent;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public PersonConsent findById(PersonConsentPK personConsentPK) {
        PersonConsent personConsent = daoPersonConsent.findById(personConsentPK);
        return personConsent;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public PersonConsent findByPersonId(String personId) {
        return daoPersonConsent.findByPersonId(personId);
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Object[] findByIdNative(String personId, int consentId) {
        return daoPersonConsent.findByIdNative(personId, consentId);
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<PersonConsent> findAllQuery() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Object[]> findAllNative() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(PersonConsent personConsent) {
        daoPersonConsent.persist(personConsent);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String personId, int consentId) {
        return daoPersonConsent.insertNative(personId, consentId);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(PersonConsentPK personConsentPK) {
        return false;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(PersonConsent personConsent) {

    }

    @Transactional(propagation= Propagation.REQUIRED)
    public PersonConsent update(PersonConsent personConsent) {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int updateEnabledNative(String personId, int consentId) {
        return daoPersonConsent.updateEnabledNative(personId, consentId);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int updateByIdQuery(PersonConsent personConsent) {
        return 0;
    }
}
