package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The DefectFlag enumeration.
 */
public enum DefectFlag {
    WELL, DEFECTED;

    public static DefectFlag get(String defectFlag) throws ValidationException {
        if (defectFlag == null)

            throw new NullPointerException("DefectFlag can't be null");

        if (defectFlag.equalsIgnoreCase("0"))
            return WELL;

        if (defectFlag.equalsIgnoreCase("1"))
            return DEFECTED;


        throw new ValidationException("Unknown defectFlag: " + defectFlag);

    }
}
