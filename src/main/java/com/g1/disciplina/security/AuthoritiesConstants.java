package com.g1.disciplina.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String STUDENT = "ROLE_STUDENT";

    public static final String PROFESSOR = "ROLE_PROFESSOR";

    public static final String SECRETARY = "ROLE_SECRETARY";

    private AuthoritiesConstants() {
    }
}
