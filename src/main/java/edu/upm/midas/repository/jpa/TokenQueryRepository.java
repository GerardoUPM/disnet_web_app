package edu.upm.midas.repository.jpa;

import edu.upm.midas.model.jpa.TokenQuery;
import edu.upm.midas.model.jpa.TokenQueryPK;

import java.util.List;

public interface TokenQueryRepository {

    TokenQuery findById(TokenQueryPK tokenQueryPK);

    List<TokenQuery> findAllQuery();

    void persist(TokenQuery tokenQuery);

    boolean deleteById(TokenQueryPK tokenQueryPK);

    void delete(TokenQuery tokenQuery);

    TokenQuery update(TokenQuery tokenQuery);

    int updateByIdQuery(TokenQueryPK tokenQueryPK);

}
