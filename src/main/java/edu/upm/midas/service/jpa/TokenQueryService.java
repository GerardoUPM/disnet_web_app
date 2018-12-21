package edu.upm.midas.service.jpa;

import edu.upm.midas.model.jpa.TokenQuery;
import edu.upm.midas.model.jpa.TokenQueryPK;

import java.util.List;

public interface TokenQueryService {

    TokenQuery findById(TokenQueryPK tokenQueryPK);

    List<TokenQuery> findAllQuery();

    void save(TokenQuery tokenQuery);

    boolean deleteById(TokenQueryPK tokenQueryPK);

    void delete(TokenQuery tokenQuery);

    TokenQuery update(TokenQuery tokenQuery);

    int updateByIdQuery(TokenQueryPK tokenQueryPK);

}
