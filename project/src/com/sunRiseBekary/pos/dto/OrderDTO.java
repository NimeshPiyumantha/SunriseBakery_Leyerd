package com.sunRiseBekary.pos.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OrderDTO {
    List<OrderDetailsDTO> orderDetails;
    private String OId;
    private String customerId;
    private String ODate;
    private String OTime;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String customerId, String orderDate, String orderTime, List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
        this.OId = orderId;
        this.customerId = customerId;
        this.ODate = orderDate;
        this.OTime = orderTime;
    }

    public OrderDTO(String orderID, String customerID, String orderDate, String orderTime) {
        this.OId = orderID;
        this.customerId = customerID;
        this.ODate = orderDate;
        this.OTime = orderTime;
    }

    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailsDTO> orderDetails) {
        this.orderDetails = orderDetails;
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
        return "OrderDTO{" +
                "orderDetails=" + orderDetails +
                ", OId='" + OId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", ODate='" + ODate + '\'' +
                ", OTime='" + OTime + '\'' +
                '}';
    }
}
