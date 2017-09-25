package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.disnetdb.TokenQuery;
import edu.upm.midas.data.relational.entities.disnetdb.TokenQueryPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.TokenQueryRepository;

import java.util.List;

public class TokenQueryRepositoryImpl extends AbstractDao<TokenQueryPK, TokenQuery>
                                        implements TokenQueryRepository{
    @Override
    public TokenQuery findById(TokenQueryPK tokenQueryPK) {
        TokenQuery tokenQuery = getByKey(tokenQueryPK);
        return tokenQuery;
    }

    @SuppressWarnings("unchecked")
    public List<TokenQuery> findAllQuery() {
        return (List<TokenQuery>) getEntityManager()
                .createNamedQuery("TokenQuery.findAll")
                .getResultList();
    }

    @Override
    public void persist(TokenQuery tokenQuery) {
        super.persist(tokenQuery);
    }

    @Override
    public boolean deleteById(TokenQueryPK tokenQueryPK) {
        return false;
    }

    @Override
    public void delete(TokenQuery tokenQuery) {
        super.delete(tokenQuery);
    }

    @Override
    public TokenQuery update(TokenQuery tokenQuery) {
        return super.update(tokenQuery);
    }

    @Override
    public int updateByIdQuery(TokenQueryPK tokenQueryPK) {
        return 0;
    }
}
