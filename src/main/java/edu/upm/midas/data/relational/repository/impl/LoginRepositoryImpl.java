package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.disnetdb.Login;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.LoginRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
@Repository("LoginRepositoryDao")
public class LoginRepositoryImpl extends AbstractDao<String, Login>
                                    implements LoginRepository{
    @Override
    public Login findById(String loginId) {
        Login login_ = getByKey(loginId);
        return login_;
    }

    @Override
    public Object[] findByIdNative(String loginId) {
        return new Object[0];
    }

    @Override
    public List<Login> findAllQuery() {
        return null;
    }

    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(Login login) {
        super.persist(login);
    }

    @Override
    public boolean deleteById(String loginId) {
        return false;
    }

    @Override
    public void delete(Login login) {

    }

    @Override
    public Login update(Login login) {
        return super.update(login);
    }

    @Override
    public int updateEnabledNative(String loginId) {
        return 0;
    }

    @Override
    public int updateByIdQuery(Login login) {
        return 0;
    }
}
