package uz.hasan.domain.enumeration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by admmp on 06.04.2017.
 */
public enum XDocTemplate {
    SHOP_DELIVERY_PRE_INVOICE("pre_invoice.docx"),
    SHOP_DELIVERY_INVOICE("invoice.docx"),
    WAREHOUSE_DELIVERY_INVOICE("invoice.docx"),
    /**/
    ;

    private String name;

    XDocTemplate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutputName(String number, String language) {
        try {
            if (language.equals("uz")) {
                return URLEncoder.encode(number, "UTF-8");
            }
            return URLEncoder.encode(number, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return number;
        }
    }
}
