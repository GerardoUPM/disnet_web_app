package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.disnetdb.Token;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.TokenRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/9/17.
 */
public class TokenRepositoryImpl extends AbstractDao<String, TokenRepository>
                                    implements TokenRepository{
    @Override
    public Token findById(String token) {
        return null;
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

    }

    @Override
    public int insertNative(String token, String type, boolean enabled, long expire, String scope, Date date) {
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
        return null;
    }

    @Override
    public int updateEnabled(String token) {
        return 0;
    }

    @Override
    public int updateByIdQuery(Token token) {
        return 0;
    }
}
