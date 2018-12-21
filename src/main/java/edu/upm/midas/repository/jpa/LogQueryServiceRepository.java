package edu.upm.midas.repository.jpa;

import edu.upm.midas.model.jpa.LogQueryService;
import edu.upm.midas.model.jpa.LogQueryServicePK;

import java.util.List;

public interface LogQueryServiceRepository {

    LogQueryService findById(LogQueryServicePK logQueryServicePK);

    List<LogQueryService> findAllQuery();

    void persist(LogQueryService logQueryService);

    boolean deleteById(LogQueryServicePK logQueryServicePK);

    void delete(LogQueryService logQueryService);

    LogQueryService update(LogQueryService logQueryService);

    int updateByIdQuery(LogQueryServicePK logQueryServicePK);

}
