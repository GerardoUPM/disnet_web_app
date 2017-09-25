package edu.upm.midas.controller;
import edu.upm.midas.data.relational.service.helper.PersonHelper;
import edu.upm.midas.token.model.JwtTokenUtil;
import edu.upm.midas.validation.model.ValidationRequest;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/validation_service", method = RequestMethod.GET, params = {"token"})
    public boolean confirmationEmail(@RequestParam(value = "token") String tokenService, Device device) throws Exception {

        System.out.println("Se va a validar una api");
        ValidationRequest validationRequest = jwtTokenUtil.getServiceJWTDecode( tokenService );

        //Inicia el registro del query

        return true;

    }

}
