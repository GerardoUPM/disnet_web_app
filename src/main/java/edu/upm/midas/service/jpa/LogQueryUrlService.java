package edu.upm.midas.service.jpa;

import edu.upm.midas.model.jpa.LogQueryUrl;
import edu.upm.midas.model.jpa.LogQueryUrlPK;

import java.util.List;

public interface LogQueryUrlService {

    LogQueryUrl findById(LogQueryUrlPK logQueryUrlPK);

    List<LogQueryUrl> findAllQuery();

    void save(LogQueryUrl logQueryUrl);

    boolean deleteById(LogQueryUrlPK logQueryUrlPK);

    void delete(LogQueryUrl logQueryUrl);

    LogQueryUrl update(LogQueryUrl logQueryUrl);

    int updateByIdQuery(LogQueryUrlPK logQueryUrlPK);

}
