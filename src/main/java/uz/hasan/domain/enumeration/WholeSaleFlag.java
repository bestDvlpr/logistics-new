package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The WholeSaleFlag enumeration.
 */
public enum WholeSaleFlag {
    RETAIL, WHOLESALE;


    public static WholeSaleFlag get(String wholeSale) throws ValidationException {
        if (wholeSale == null)
            throw new NullPointerException("DocType can't be null");
        if (wholeSale.equalsIgnoreCase("0"))
            return RETAIL;
        if (wholeSale.equalsIgnoreCase("1"))
            return WHOLESALE;

        throw new ValidationException("Unknown type: " + wholeSale);

    }

}
