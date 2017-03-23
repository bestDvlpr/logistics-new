package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The CarStatus enumeration.
 */
public enum CarStatus {
    IDLE, BUSY;

    public static CarStatus get(String carStatus) throws ValidationException {
        if (carStatus == null)

            throw new NullPointerException("CarStatus can't be null");
        if (carStatus.equalsIgnoreCase("0"))
            return IDLE;

        if (carStatus.equalsIgnoreCase("1"))
            return BUSY;

        throw new ValidationException("Unknown carStatus: " + carStatus);

    }
}
