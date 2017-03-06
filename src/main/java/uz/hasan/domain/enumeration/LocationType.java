package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The LocationType enumeration.
 */
public enum LocationType {
    COUNTRY,REGION,CITY,DISTRICT;

    /**
     * LocationType - тип местоположении (значения COUNTRY – Страна, REGION – Регион, CITY – город, DISTRICT – район)
     */

    public static LocationType get(String type) throws ValidationException {
        if (type == null)

            throw new NullPointerException("LocationType can't be null");
        if (type.equalsIgnoreCase("COUNTRY"))
            return COUNTRY;

        if (type.equalsIgnoreCase("REGION"))
            return REGION;
        if (type.equalsIgnoreCase("CITY"))
            return CITY;
        if (type.equalsIgnoreCase("DISTRICT"))
            return DISTRICT;

        throw new ValidationException("Unknown LocationType: " + type);

    }
}
