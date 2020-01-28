package edu.upm.midas.repository.jpa;
import edu.upm.midas.model.jpa.Consent;

import java.util.List;

/**
 * Created by gerardo on 20/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className ConsentRepository
 * @see
 */
public interface ConsentRepository {

    Consent findById(int consentId);

    Consent findByFormName(String formName);

    Object[] findByIdNative(int consentId);//por consentId
    
    List<Consent> findAllQuery();

    List<Object[]> findAllNative();

    void persist(Consent consent);
    
    int insertNative(String institution, String description, String formName);
    
    boolean deleteById(int consentId);

    void delete(Consent consent);

    Consent update(Consent consent);

    int updateByIdQuery(Consent consent);
    
}
