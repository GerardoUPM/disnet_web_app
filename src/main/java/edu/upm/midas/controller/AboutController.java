package edu.upm.midas.controller;

import edu.upm.midas.client_modules.disnet_query.texts_extraction.api_response.DisnetService;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.DatabaseStatisticsResponse;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.Snapshot;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.Source;
import edu.upm.midas.common.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

//import org.springframework.token.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by gerardo on 12/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className IndexController
 * @see
 */
@Controller
public class AboutController {

    @Autowired
    private DisnetService disnetService;

    @Autowired
    private Common common;

    @Value("${my.service.client.disnet.url}")
    private String service_client_url;
    @Value("${my.service.client.disnet.last.desc_statistics.path}")
    private String service_client_last_json_desc_statistic_path;

    @PostConstruct
    public void setupInfo(){
        System.out.println(
                "DISNET SERVICE: MAIN URL: " + this.service_client_url + "\n" +
                        "REPORT PATH: " + this.service_client_last_json_desc_statistic_path);
    }

    @RequestMapping("/about")
    public ModelAndView about(Model model){
        ModelAndView modelAndView = new ModelAndView();
        DatabaseStatisticsResponse statisticsResponse = disnetService.getLastDatabaseStatisticsJSON();
//        System.out.println(statisticsResponse);
        if (statisticsResponse!=null) {
            int numTotSanpshots = 0;
            for (Source source : statisticsResponse.getSources()) {
                numTotSanpshots += source.getSnapshotCount();
            }
            //<editor-fold desc="FORMAR Y ENVIAR CONTENIDO">
            modelAndView.addObject("statisticsData", statisticsResponse);
            modelAndView.addObject("numTotSnapshots", numTotSanpshots);
            //</editor-fold>

            modelAndView.setViewName("about");
        }else{
            modelAndView.setViewName("index");
        }

        return modelAndView;
    }

}
