package edu.upm.midas.service.jpa;

import edu.upm.midas.model.jpa.LogQuery;
import edu.upm.midas.model.user.TransactionHistory;

import java.util.List;

public interface LogQuery_Service {

    LogQuery findById(String queryId);

    List<LogQuery> findAllQuery();

    List<TransactionHistory> findByTokenNative(String token);

    void save(LogQuery logQuery);

    boolean deleteById(String queryId);

    void delete(LogQuery logQuery);

    LogQuery update(LogQuery logQuery);

    int updateByIdQuery(String queryId);

}
