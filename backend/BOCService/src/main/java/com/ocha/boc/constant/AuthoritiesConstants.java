package com.ocha.boc.constant;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String PARTICIPANT = "ROLE_PARTICIPANT";

    public static final String ASSESSOR = "ROLE_ASSESSOR";
    
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
