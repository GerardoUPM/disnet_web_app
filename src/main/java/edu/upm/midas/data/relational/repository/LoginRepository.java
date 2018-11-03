package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.disnetdb.Login;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public interface LoginRepository {

    Login findById(String loginId);

    Object[] findByIdNative(String loginId);

    List<Login> findAllQuery();

    List<Object[]> findAllNative();

    void persist(Login login);

//    int insertNative(String login, String type, boolean enabled, long expiration, String scope, Date date);

    boolean deleteById(String loginId);

    void delete(Login login);

    Login update(Login login);

    int updateEnabledNative(String loginId);

    int updateByIdQuery(Login login);

}
