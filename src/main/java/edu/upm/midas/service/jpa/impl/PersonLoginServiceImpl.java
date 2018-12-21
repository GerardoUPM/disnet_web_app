package edu.upm.midas.service.jpa.impl;


import edu.upm.midas.model.jpa.PersonLogin;
import edu.upm.midas.model.jpa.PersonLoginPK;
import edu.upm.midas.repository.jpa.PersonLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 24/9/17.
 */
@Service("personLoginService")
public class PersonLoginServiceImpl implements PersonLoginService {

    @Autowired
    private PersonLoginRepository daoPersonLogin;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public PersonLogin findById(PersonLoginPK personLoginPK) {
        PersonLogin personLogin = daoPersonLogin.findById(personLoginPK);
        return personLogin;
    }

    @Override
    public PersonLogin findByPersonId(String personId) {
        return daoPersonLogin.findByPersonId(personId);
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Object[] findByIdNative(String personId, String loginId) {
        return daoPersonLogin.findByIdNative(personId, loginId);
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<PersonLogin> findAllQuery() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Object[]> findAllNative() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(PersonLogin personLogin) {
        daoPersonLogin.persist(personLogin);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public int insertNative(String personId, String loginId, boolean enabled, Date date) {
        return 0;
//        return daoPersonLogin.insertNative(personId, loginId, enabled, date);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public boolean deleteById(PersonLoginPK personLoginPK) {
        return false;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void delete(PersonLogin personLogin) {

    }

    @Transactional(propagation=Propagation.REQUIRED)
    public PersonLogin update(PersonLogin personLogin) {
        return null;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public int updateEnabledNative(String personId, String token, boolean enabled, Date date, Date datetime) {
        return daoPersonLogin.updateEnabledNative(personId, token, enabled, date, datetime);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public int updateByIdQuery(PersonLogin personLogin) {
        return 0;
    }
}
