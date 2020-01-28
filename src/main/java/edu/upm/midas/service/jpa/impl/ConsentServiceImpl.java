package edu.upm.midas.service.jpa.impl;
import edu.upm.midas.model.jpa.Consent;
import edu.upm.midas.repository.jpa.ConsentRepository;
import edu.upm.midas.service.jpa.ConsentService;
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
 * @className ConsentServiceImpl
 * @see
 */
@Service("consentService")
public class ConsentServiceImpl implements ConsentService {

    @Autowired
    private ConsentRepository daoConsentRepository;


    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Consent findById(int consentId) {
        Consent consent = daoConsentRepository.findById(consentId);
        return consent;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Object[] findByIdNative(int consentId) {
        return new Object[0];
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Consent findByFormName(String formName) {
        return daoConsentRepository.findByFormName(formName);
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Consent> findAllQuery() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Object[]> findAllNative() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public void save(Consent consent) {
        daoConsentRepository.persist(consent);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String institution, String description, String formName) {
        return 0;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(int consentId) {
        return false;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(Consent consent) {

    }

    @Transactional(propagation= Propagation.REQUIRED)
    public Consent update(Consent consent) {
        return daoConsentRepository.update(consent);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int updateByIdQuery(Consent consent) {
        return 0;
    }
}
