package edu.upm.midas.client_modules.disnet_query.texts_extraction.api_response;


import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.DatabaseStatisticsResponse;

/**
 * Created by gerardo on 12/02/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project dgsvp-rest
 * @className MayoClinicTextsExtractionService
 * @see
 */
public interface DisnetService {

    DatabaseStatisticsResponse getLastDatabaseStatisticsJSON();
}
