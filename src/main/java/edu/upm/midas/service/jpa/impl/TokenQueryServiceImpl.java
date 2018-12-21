package edu.upm.midas.service.jpa.impl;

import edu.upm.midas.model.jpa.TokenQuery;
import edu.upm.midas.model.jpa.TokenQueryPK;
import edu.upm.midas.repository.jpa.TokenQueryRepository;
import edu.upm.midas.service.jpa.TokenQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("tokenQueryService")
public class TokenQueryServiceImpl implements TokenQueryService {

    @Autowired
    private TokenQueryRepository daoTokenQuery;


    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public TokenQuery findById(TokenQueryPK tokenQueryPK) {
        TokenQuery tokenQuery = daoTokenQuery.findById(tokenQueryPK);
        return tokenQuery;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<TokenQuery> findAllQuery() {
        return daoTokenQuery.findAllQuery();
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(TokenQuery tokenQuery) {
        daoTokenQuery.persist(tokenQuery);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(TokenQueryPK tokenQueryPK) {
        return false;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(TokenQuery tokenQuery) {
        daoTokenQuery.delete(tokenQuery);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public TokenQuery update(TokenQuery tokenQuery) {
        return daoTokenQuery.update(tokenQuery);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int updateByIdQuery(TokenQueryPK tokenQueryPK) {
        return 0;
    }
}
