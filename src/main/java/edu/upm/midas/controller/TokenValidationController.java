package edu.upm.midas.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.common.utils.TimeProvider;
import edu.upm.midas.common.utils.UniqueId;
import edu.upm.midas.data.relational.entities.disnetdb.LogQuery;
import edu.upm.midas.data.relational.entities.disnetdb.LogQueryService;
import edu.upm.midas.data.relational.service.LogQuery_Service;
import edu.upm.midas.data.relational.service.helper.PersonHelper;
import edu.upm.midas.token.model.JwtTokenUtil;
import edu.upm.midas.validation.model.ValidationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by gerardo on 25/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className TokenValidationController
 * @see
 */
@Controller
public class TokenValidationController {

    private static final Logger logger = LoggerFactory.getLogger(PersonHelper.class);
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private LogQuery_Service logQuery_Service;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private TimeProvider timeProvider;


    @RequestMapping(value = "/validation_service", method = RequestMethod.GET, params = {"token"})
    public boolean confirmationEmail(@RequestParam(value = "token") String tokenService, Device device) throws Exception {

        System.out.println("Se va a validar una api");
        ValidationRequest validationRequest = jwtTokenUtil.getServiceJWTDecode( tokenService );
        String personId = jwtTokenUtil.getEmailWithJWTDecode( validationRequest.getToken() );

        LogQuery logQuery = new LogQuery();
        logQuery.setQueryId( uniqueId.generate(35) );
        logQuery.setRequest( validationRequest.getRequest() );
        logQuery.setDate( timeProvider.getNow() );
        logQuery.setDatetime( timeProvider.getTimestamp() );
        logger.info( "Object Persist: {}",objectMapper.writeValueAsString( logQuery ) );
        logQuery_Service.save( logQuery );
        logger.info( "Object Persist: {}",objectMapper.writeValueAsString( logQuery ) );


        //Inicia el registro del query

        return true;

    }

}
