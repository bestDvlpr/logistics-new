package uz.hasan.service.dto;

import java.io.Serializable;

/**
 * Created by User on 3/7/2017.
 */
public class JsonApp implements Serializable {

    private Boolean success = false;
    private int errorCode = 500;
    private Object data = null;

    public JsonApp(Boolean success, int errorCode, Object data) {
        this.success = success;
        this.errorCode = errorCode;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
