package edu.upm.midas.repository.jpa;

import edu.upm.midas.model.jpa.Token;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public interface TokenRepository {

    Token findById(String token);

    Object[] findByIdNative(String token);

    List<Token> findAllQuery();

    List<Object[]> findAllNative();

    void persist(Token token);

    int insertNative(String token, String type, boolean enabled, long expiration, String scope, Date date);

    boolean deleteById(String token);

    void delete(Token token);

    Token update(Token token);

    int updateEnabledNative(String token);

    int updateByIdQuery(Token token);

}
