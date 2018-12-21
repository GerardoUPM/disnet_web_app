package edu.upm.midas.repository.jpa.impl;

import edu.upm.midas.model.jpa.LogQuery;
import edu.upm.midas.repository.jpa.AbstractDao;
import edu.upm.midas.repository.jpa.LogQueryRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LogQueryRepositoryDao")
public class LogQueryRepositoryImpl extends AbstractDao<String, LogQuery>
                                    implements LogQueryRepository{
    @Override
    public LogQuery findById(String queryId) {
        LogQuery logQuery = getByKey(queryId);
        return logQuery;
    }

    @SuppressWarnings("unchecked")
    public List<LogQuery> findAllQuery() {
        return (List<LogQuery>) getEntityManager()
                .createNamedQuery("LogQuery.findAll")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> findByTokenNative(String token) {
        List<Object[]> queries = null;
        List<Object[]> queryList = (List<Object[]>) getEntityManager()
                .createNamedQuery("LogQuery.findByTokenNative")
                .setParameter("token", token)
                //.setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(queryList))
            queries = queryList;

        return queries;
    }

    @Override
    public void persist(LogQuery logQuery) {
        super.persist(logQuery);
    }

    @Override
    public boolean deleteById(String queryId) {
        return false;
    }

    @Override
    public void delete(LogQuery logQuery) {
        super.delete(logQuery);
    }

    @Override
    public LogQuery update(LogQuery logQuery) {
        return super.update(logQuery);
    }

    @Override
    public int updateByIdQuery(String queryId) {
        return 0;
    }
}
