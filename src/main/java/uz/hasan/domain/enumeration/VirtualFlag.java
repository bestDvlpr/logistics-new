package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The VirtualFlag enumeration.
 */
public enum VirtualFlag {
    SOLD_PHISICALLY, SOLD_VIRTUALLY;

    public static VirtualFlag get(String virtualFlag) throws ValidationException {
        if (virtualFlag == null)

            throw new NullPointerException("DefectFlag can't be null");
        if (virtualFlag.equalsIgnoreCase("0"))
            return SOLD_PHISICALLY;

        if (virtualFlag.equalsIgnoreCase("1"))
            return SOLD_VIRTUALLY;

        throw new ValidationException("Unknown virtualFlag: " + virtualFlag);

    }
}
