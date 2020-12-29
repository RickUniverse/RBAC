package org.atcrowdfunding.bean;

/**
 * @author lijichen
 * @date 2020/12/22 - 19:47
 */
public class AjaxSuccess {
    private Boolean success;
    private Object data;

    public AjaxSuccess(Boolean success) {
        this.success = success;
    }

    public AjaxSuccess() {
    }

    public AjaxSuccess(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public AjaxSuccess setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Object getData() {
        return data;
    }

    public AjaxSuccess setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "AjaxSuccess{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}