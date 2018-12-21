package edu.upm.midas.service.jpa;

import edu.upm.midas.model.jpa.SystemService;

import java.util.List;

public interface SystemService_Service {

    SystemService findById(String serviceId);

    List<SystemService> findAllQuery();

    void save(SystemService systemService);

    boolean deleteById(String serviceId);

    void delete(SystemService systemService);

    SystemService update(SystemService systemService);

    int updateByIdQuery(String serviceId);

}
