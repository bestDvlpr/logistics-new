package uz.hasan.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String DISPATCHER = "ROLE_DISPATCHER";

    public static final String CASHIER = "ROLE_CASHIER";

    public static final String CREDIT = "ROLE_CREDIT";

    public static final String WAREHOUSE = "ROLE_WAREHOUSE";

    private AuthoritiesConstants() {
    }
}
