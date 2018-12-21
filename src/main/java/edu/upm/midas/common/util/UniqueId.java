package edu.upm.midas.common.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class UniqueId {

    public String generate(int length){
        return RandomStringUtils.randomAlphanumeric( length ).toLowerCase();
    }

}
