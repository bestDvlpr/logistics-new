package uz.hasan.domain.enumeration;

import javax.xml.bind.ValidationException;

/**
 * The PaymentType enumeration.
 */
public enum PaymentType {
    CASH, CARD, ZSRT, ZCUP, ZSRTC, ZBAL, ZCUPS, LOYALTY_CARD;

    /**
     * Type - тип оплаты (значения CASH – наличные, CARD – карточка, ZSRT – подарочный сертификат, ZCUP – купон, ZSRTC – сдача с сетртификата, ZCUPS – сдача с купона, ZBAL – оплата баллами )
     */

    public static PaymentType get(String type) throws ValidationException {
        if (type == null)

            throw new NullPointerException("PaymentType can't be null");
        if (type.equalsIgnoreCase("CASH"))
            return CASH;

        if (type.equalsIgnoreCase("CARD"))
            return CARD;
        if (type.equalsIgnoreCase("ZSRT"))
            return ZSRT;
        if (type.equalsIgnoreCase("ZCUP"))
            return ZCUP;
        if (type.equalsIgnoreCase("ZSRTC"))
            return ZSRTC;
        if (type.equalsIgnoreCase("ZBAL"))
            return ZBAL;
        if (type.equalsIgnoreCase("ZCUPS"))
            return ZCUPS;
        if (type.equalsIgnoreCase("LOYALTY_CARD"))
            return LOYALTY_CARD;

        throw new ValidationException("Unknown PaymentType: " + type);

    }
}
