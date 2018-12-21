package edu.upm.midas.client_modules.disnet_query.texts_extraction.client.fallback;

import edu.upm.midas.client_modules.disnet_query.texts_extraction.client.DisnetClient;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.DatabaseStatisticsResponse;
import org.springframework.stereotype.Component;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpClientFallback
 * @see
 */
@Component
public class DisnetClientFallback implements DisnetClient {

    @Override
    public DatabaseStatisticsResponse getLastDatabaseStatisticsJSON() {
        return new DatabaseStatisticsResponse();
    }
}

