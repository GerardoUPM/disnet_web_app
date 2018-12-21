package edu.upm.midas.client_modules.disnet_query.texts_extraction.api_response.impl;


import edu.upm.midas.client_modules.disnet_query.texts_extraction.api_response.DisnetService;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.client.DisnetClient;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.DatabaseStatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo on 12/02/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project dgsvp-rest
 * @className MayoClinicTextsExtractionServiceImpl
 * @see
 */
@Service
public class DisnetServiceImpl implements DisnetService {

    @Autowired
    private DisnetClient disnetClient;

    @Override
    public DatabaseStatisticsResponse getLastDatabaseStatisticsJSON() {
        return disnetClient.getLastDatabaseStatisticsJSON();
    }
}
