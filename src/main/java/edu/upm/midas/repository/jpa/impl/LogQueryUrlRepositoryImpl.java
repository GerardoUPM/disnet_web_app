package edu.upm.midas.repository.jpa.impl;

import edu.upm.midas.model.jpa.LogQueryUrl;
import edu.upm.midas.model.jpa.LogQueryUrlPK;
import edu.upm.midas.repository.jpa.AbstractDao;
import edu.upm.midas.repository.jpa.LogQueryUrlRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LogQueryUrlRepositoryDao")
public class LogQueryUrlRepositoryImpl extends AbstractDao<LogQueryUrlPK, LogQueryUrl>
                                        implements LogQueryUrlRepository{
    @Override
    public LogQueryUrl findById(LogQueryUrlPK logQueryUrlPK) {
        LogQueryUrl logQueryUrl = getByKey(logQueryUrlPK);
        return logQueryUrl;
    }

    @SuppressWarnings("unchecked")
    public List<LogQueryUrl> findAllQuery() {
        return (List<LogQueryUrl>) getEntityManager()
                .createNamedQuery("LogQueryUrl.findAll")
                .getResultList();
    }

    @Override
    public void persist(LogQueryUrl logQueryUrl) {
        super.persist(logQueryUrl);
    }

    @Override
    public boolean deleteById(LogQueryUrlPK logQueryUrlPK) {
        return false;
    }

    @Override
    public void delete(LogQueryUrl logQueryUrl) {
        super.delete(logQueryUrl);
    }

    @Override
    public LogQueryUrl update(LogQueryUrl logQueryUrl) {
        return super.update(logQueryUrl);
    }

    @Override
    public int updateByIdQuery(LogQueryUrlPK logQueryUrlPK) {
        return 0;
    }
}
