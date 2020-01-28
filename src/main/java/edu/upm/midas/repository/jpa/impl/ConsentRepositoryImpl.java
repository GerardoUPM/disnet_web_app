package edu.upm.midas.repository.jpa.impl;
import edu.upm.midas.model.jpa.Consent;
import edu.upm.midas.model.jpa.PersonConsent;
import edu.upm.midas.repository.jpa.AbstractDao;
import edu.upm.midas.repository.jpa.ConsentRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 28/01/2020.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className ConsentRepositoryImpl
 * @see
 */
@Repository("ConsentRepositoryDao")
public class ConsentRepositoryImpl extends AbstractDao<Integer, Consent> implements ConsentRepository {
    @Override
    public Consent findById(int consentId) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Consent findByFormName(String formName) {
        Consent consent = null;
        List<Consent> consentList = (List<Consent>) getEntityManager()
                .createNamedQuery("Consent.findByFormName")
                .setParameter("formName", formName)
                .getResultList();
        if (CollectionUtils.isNotEmpty(consentList))
            consent = consentList.get(0);
        return consent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] findByIdNative(int consentId) {
        return new Object[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Consent> findAllQuery() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(Consent consent) {
        super.persist(consent);
    }

    @Override
    public int insertNative(String institution, String description, String formName) {
        return 0;
    }

    @Override
    public boolean deleteById(int consentId) {
        return false;
    }

    @Override
    public void delete(Consent consent) {

    }

    @Override
    public Consent update(Consent consent) {
        return super.update(consent);
    }

    @Override
    public int updateByIdQuery(Consent consent) {
        return 0;
    }
}
