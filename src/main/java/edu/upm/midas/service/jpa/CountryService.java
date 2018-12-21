package edu.upm.midas.service.jpa;
import edu.upm.midas.model.jpa.Country;

import java.util.List;

/**
 * Created by gerardo on 25/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className CountryService
 * @see
 */
public interface CountryService {

    List<Country> findAll();

}
