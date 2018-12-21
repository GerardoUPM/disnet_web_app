package edu.upm.midas.repository.jpa.impl;

import edu.upm.midas.model.jpa.LogQueryService;
import edu.upm.midas.model.jpa.LogQueryServicePK;
import edu.upm.midas.repository.jpa.AbstractDao;
import edu.upm.midas.repository.jpa.LogQueryServiceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LogQueryServiceRepositoryDao")
public class LogQueryServiceRepositoryImpl extends AbstractDao<LogQueryServicePK, LogQueryService>
                                            implements LogQueryServiceRepository{

    @Override
    public LogQueryService findById(LogQueryServicePK logQueryServicePK) {
        LogQueryService logQueryService = getByKey(logQueryServicePK);
        return logQueryService;
    }

    @SuppressWarnings("unchecked")
    public List<LogQueryService> findAllQuery() {
        return (List<LogQueryService>) getEntityManager()
                .createNamedQuery("LogQueryService.findAll")
                .getResultList();
    }

    @Override
    public void persist(LogQueryService logQueryService) {
        super.persist(logQueryService);
    }

    @Override
    public boolean deleteById(LogQueryServicePK logQueryServicePK) {
        return false;
    }

    @Override
    public void delete(LogQueryService logQueryService) {
        super.delete(logQueryService);
    }

    @Override
    public LogQueryService update(LogQueryService logQueryService) {
        return super.update(logQueryService);
    }

    @Override
    public int updateByIdQuery(LogQueryServicePK logQueryServicePK) {
        return 0;
    }
}
