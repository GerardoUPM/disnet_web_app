package edu.upm.midas.client_modules.disnet_query.texts_extraction.client;



import edu.upm.midas.client_modules.disnet_query.texts_extraction.client.fallback.DisnetClientFallback;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.DatabaseStatisticsResponse;
import edu.upm.midas.configuration.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project dgsvp
 * @className WikipediaTextsExtractionClient
 * @see
 */

@FeignClient(name = "${my.service.client.disnet.name}",
        url = "${my.service.client.disnet.url}",
        fallback = DisnetClientFallback.class,
        configuration = FeignConfiguration.class)

public interface DisnetClient {

    @RequestMapping(value = "${my.service.client.disnet.last.desc_statistics.path}", method = RequestMethod.GET)
    DatabaseStatisticsResponse getLastDatabaseStatisticsJSON();

}
