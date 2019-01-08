package com.face.facepay.pojo;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private long orderId;
    private long userId;
    private byte[] commodityList;//此处以json格式字符串存入
    private String orderCode;
    private String time;
    private double totalPrice;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public byte[] getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(byte[] commodityList) {
        this.commodityList = commodityList;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
