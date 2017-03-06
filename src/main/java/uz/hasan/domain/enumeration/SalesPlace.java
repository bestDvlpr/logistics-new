package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The SalesPlace enumeration.
 */
public enum SalesPlace {
    STORE,WAREHOUSE;

    public static SalesPlace get(String place) throws ValidationException {
        if (place == null)

            throw new NullPointerException("SalesPlace can't be null");
        if (place.equalsIgnoreCase("0"))
            return STORE;

        if (place.equalsIgnoreCase("1"))
            return WAREHOUSE;

        throw new ValidationException("Unknown Place: " + place);

    }
}
