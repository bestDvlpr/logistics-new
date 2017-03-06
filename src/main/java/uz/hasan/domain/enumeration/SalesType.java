package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The SalesType enumeration.
 */
public enum SalesType {
    TAKEOUT_IN_TIME, TAKEOUT, DELIVERY;

    public static SalesType get(String type) throws ValidationException {
        if (type == null)
            throw new NullPointerException("SalesType can't be null");
        if (type.equalsIgnoreCase("0"))
            return TAKEOUT_IN_TIME;
        if (type.equalsIgnoreCase("1"))
            return TAKEOUT;
        if (type.equalsIgnoreCase("2"))
            return DELIVERY;

        throw new ValidationException("Unknown type: " + type);

    }
}
