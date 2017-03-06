package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The DocType enumeration.
 */
public enum DocType {
    RETURN, SALES;


    public static DocType get(String type) throws ValidationException {
        if (type == null)
            throw new NullPointerException("DocType can't be null");
        if (type.equalsIgnoreCase("+"))
            return SALES;
        if (type.equalsIgnoreCase("-"))
            return RETURN;

        throw new ValidationException("Unknown type: " + type);

    }

}
