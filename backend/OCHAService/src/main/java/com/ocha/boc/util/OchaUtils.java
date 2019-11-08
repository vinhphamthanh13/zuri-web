package com.ocha.boc.util;

import java.util.UUID;

public class OchaUtils {

    public static String generateUUIDCode(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
