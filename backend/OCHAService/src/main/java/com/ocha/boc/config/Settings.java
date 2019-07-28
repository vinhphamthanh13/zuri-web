package com.ocha.boc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Settings {

    @Value(value = "${account.security.api.key}")
    private String accountSecurityAPIKey;

    public String getAuthyId(){
        return accountSecurityAPIKey;
    }
}
