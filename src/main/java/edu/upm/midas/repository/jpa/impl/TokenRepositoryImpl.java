package edu.upm.midas.repository.jpa.impl;

import edu.upm.midas.model.jpa.Token;
import edu.upm.midas.repository.jpa.AbstractDao;
import edu.upm.midas.repository.jpa.TokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
@Repository("TokenRepositoryDao")
public class TokenRepositoryImpl extends AbstractDao<String, Token>
                                    implements TokenRepository{
    @Override
    public Token findById(String token) {
        Token token_ = getByKey(token);
        return token_;
    }

    @Override
    public Object[] findByIdNative(String token) {
        return new Object[0];
    }

    @Override
    public List<Token> findAllQuery() {
        return null;
    }

    @Override
    public List<Object[]> findAllNative() {
        return null;
    }

    @Override
    public void persist(Token token) {
        super.persist(token);
    }

    @Override
    public int insertNative(String token, String type, boolean enabled, long expiration, String scope, Date date) {
        return 0;
    }

    @Override
    public boolean deleteById(String token) {
        return false;
    }

    @Override
    public void delete(Token token) {

    }

    @Override
    public Token update(Token token) {
        return super.update(token);
    }

    @Override
    public int updateEnabledNative(String token) {
        return 0;
    }

    @Override
    public int updateByIdQuery(Token token) {
        return 0;
    }
}
