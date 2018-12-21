package edu.upm.midas.service.jpa.impl;

import edu.upm.midas.model.jpa.LogQueryUrl;
import edu.upm.midas.model.jpa.LogQueryUrlPK;
import edu.upm.midas.repository.jpa.LogQueryUrlRepository;
import edu.upm.midas.service.jpa.LogQueryUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("logQueryUrlService")
public class LogQueryUrlServiceImpl implements LogQueryUrlService {

    @Autowired
    private LogQueryUrlRepository daoLogQueryUrl;


    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public LogQueryUrl findById(LogQueryUrlPK logQueryUrlPK) {
        LogQueryUrl logQueryUrl = daoLogQueryUrl.findById(logQueryUrlPK);
        return logQueryUrl;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<LogQueryUrl> findAllQuery() {
        return daoLogQueryUrl.findAllQuery();
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(LogQueryUrl logQueryUrl) {
        daoLogQueryUrl.persist(logQueryUrl);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(LogQueryUrlPK logQueryUrlPK) {
        return false;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void delete(LogQueryUrl logQueryUrl) {
        daoLogQueryUrl.delete(logQueryUrl);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public LogQueryUrl update(LogQueryUrl logQueryUrl) {
        return daoLogQueryUrl.update(logQueryUrl);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int updateByIdQuery(LogQueryUrlPK logQueryUrlPK) {
        return 0;
    }
}
