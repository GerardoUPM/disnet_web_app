package edu.upm.midas.repository.jpa;

import edu.upm.midas.model.jpa.LogQueryUrl;
import edu.upm.midas.model.jpa.LogQueryUrlPK;

import java.util.List;

public interface LogQueryUrlRepository {

    LogQueryUrl findById(LogQueryUrlPK logQueryUrlPK);

    List<LogQueryUrl> findAllQuery();

    void persist(LogQueryUrl logQueryUrl);

    boolean deleteById(LogQueryUrlPK logQueryUrlPK);

    void delete(LogQueryUrl logQueryUrl);

    LogQueryUrl update(LogQueryUrl logQueryUrl);

    int updateByIdQuery(LogQueryUrlPK logQueryUrlPK);

}
