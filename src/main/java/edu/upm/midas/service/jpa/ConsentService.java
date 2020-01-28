package edu.upm.midas.service.jpa;
import edu.upm.midas.model.jpa.Consent;

import java.util.List;

/**
 * Created by gerardo on 20/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className ConsentService
 * @see
 */
public interface ConsentService {

    Consent findById(int consentId);

    Object[] findByIdNative(int consentId);//por consentId

    Consent findByFormName(String formName);

    List<Consent> findAllQuery();

    List<Object[]> findAllNative();

    void save(Consent consent);

    int insertNative(String institution, String description, String formName);

    boolean deleteById(int consentId);

    void delete(Consent consent);

    Consent update(Consent consent);

    int updateByIdQuery(Consent consent);

}
