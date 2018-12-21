package edu.upm.midas.service.jpa.impl;

import edu.upm.midas.model.jpa.Login;
import edu.upm.midas.repository.jpa.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 24/9/17.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {


    @Autowired
    private LoginRepository daoLogin;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Login findById(String loginId) {
        Login oLogin = daoLogin.findById(loginId);
        return oLogin;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Object[] findByIdNative(String loginId) {
        return daoLogin.findByIdNative(loginId);
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Login> findAllQuery() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Object[]> findAllNative() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Login login) {
        daoLogin.persist(login);
    }

//    @Transactional(propagation=Propagation.REQUIRED)
//    public int insertNative(String login, String type, boolean enabled, long expiration, String scope, Date date) {
//        return daoLogin.insertNative(login, type, enabled, expiration, scope, date);
//    }

    @Transactional(propagation=Propagation.REQUIRED)
    public boolean deleteById(String loginId) {
        return false;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void delete(Login login) {

    }

    @Transactional(propagation=Propagation.REQUIRED)
    public Login update(Login login) {
        return null;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public int updateEnabledNative(String loginId) {
        return daoLogin.updateEnabledNative(loginId);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public int updateByIdQuery(Login login) {
        return 0;
    }
}
