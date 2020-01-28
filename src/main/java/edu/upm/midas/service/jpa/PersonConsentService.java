package edu.upm.midas.service.jpa;

import edu.upm.midas.model.jpa.PersonConsent;
import edu.upm.midas.model.jpa.PersonConsentPK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public interface PersonConsentService {

    PersonConsent findById(PersonConsentPK personConsentPK);

    PersonConsent findByPersonId(String personId);

    Object[] findByIdNative(String personId, int consentId);

    List<PersonConsent> findAllQuery();

    List<Object[]> findAllNative();

    void save(PersonConsent personConsent);

    int insertNative(String personId, int consentId);

    boolean deleteById(PersonConsentPK personConsentPK);

    void delete(PersonConsent personConsent);

    PersonConsent update(PersonConsent personConsent);

    int updateEnabledNative(String personId, int consentId);

    int updateByIdQuery(PersonConsent personConsent);

}
