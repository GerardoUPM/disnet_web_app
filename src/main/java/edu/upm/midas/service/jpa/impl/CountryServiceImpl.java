package edu.upm.midas.service.jpa.impl;
import edu.upm.midas.model.jpa.Country;
import edu.upm.midas.repository.jpa.CountryRepository;
import edu.upm.midas.service.jpa.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gerardo on 25/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className CountryServiceImpl
 * @see
 */
@Service("serviceService")
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository daoCountry;

    @Override
    public List<Country> findAll() {
        return daoCountry.findAll();
    }
}
