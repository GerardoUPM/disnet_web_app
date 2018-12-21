package edu.upm.midas.repository.jpa;

import edu.upm.midas.model.jpa.SystemService;

import java.util.List;

public interface SystemServiceRepository {

    SystemService findById(String serviceId);

    List<SystemService> findAllQuery();

    void persist(SystemService systemService);

    boolean deleteById(String serviceId);

    void delete(SystemService systemService);

    SystemService update(SystemService systemService);

    int updateByIdQuery(String serviceId);

}
