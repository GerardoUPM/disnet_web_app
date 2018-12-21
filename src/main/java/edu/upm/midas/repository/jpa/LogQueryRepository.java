package edu.upm.midas.repository.jpa;

import edu.upm.midas.model.jpa.LogQuery;

import java.util.List;

public interface LogQueryRepository {

    LogQuery findById(String queryId);

    List<LogQuery> findAllQuery();

    List<Object[]> findByTokenNative(String token);

    void persist(LogQuery logQuery);

    boolean deleteById(String queryId);

    void delete(LogQuery logQuery);

    LogQuery update(LogQuery logQuery);

    int updateByIdQuery(String queryId);

}
