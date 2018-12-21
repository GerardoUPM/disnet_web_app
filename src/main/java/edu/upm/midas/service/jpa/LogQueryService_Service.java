package edu.upm.midas.service.jpa;

import edu.upm.midas.model.jpa.LogQueryService;
import edu.upm.midas.model.jpa.LogQueryServicePK;

import java.util.List;

public interface LogQueryService_Service {

    LogQueryService findById(LogQueryServicePK logQueryServicePK);

    List<LogQueryService> findAllQuery();

    void save(LogQueryService logQueryService);

    boolean deleteById(LogQueryServicePK logQueryServicePK);

    void delete(LogQueryService logQueryService);

    LogQueryService update(LogQueryService logQueryService);

    int updateByIdQuery(LogQueryServicePK logQueryServicePK);

}
