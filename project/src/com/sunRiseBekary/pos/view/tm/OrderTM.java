package com.sunRiseBekary.pos.view.tm;

/**
 * @author : Nimesh Piyumantha
 * @since : 0.1.0
 **/
public class OrderTM {
    private String OId;
    private String customerId;
    private String ODate;
    private String OTime;

    public OrderTM() {
    }

    public OrderTM(String OId, String customerId, String ODate, String OTime) {
        this.OId = OId;
        this.customerId = customerId;
        this.ODate = ODate;
        this.OTime = OTime;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getODate() {
        return ODate;
    }

    public void setODate(String ODate) {
        this.ODate = ODate;
    }

    public String getOTime() {
        return OTime;
    }

    public void setOTime(String OTime) {
        this.OTime = OTime;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "OId='" + OId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", ODate='" + ODate + '\'' +
                ", OTime='" + OTime + '\'' +
                '}';
    }
}
