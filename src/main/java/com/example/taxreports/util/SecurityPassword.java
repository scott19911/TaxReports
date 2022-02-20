package com.example.taxreports.util;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
public class SecurityPassword {

    public String getSalt(){
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(secureRandom.nextInt(Integer.MAX_VALUE));
    }

    public String getHashPassword (String pass){
        return DigestUtils.sha256Hex(pass);
    }

}
