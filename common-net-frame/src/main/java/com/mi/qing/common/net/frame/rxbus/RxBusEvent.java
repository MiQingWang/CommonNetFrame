package com.mi.qing.common.net.frame.rxbus;

import java.util.List;

/***
 * rxbus事件
 * @param <T>
 */
public class RxBusEvent<T> {

    public static <T> RxBusEvent<T> newInstance() {
        return new RxBusEvent<>();
    }

    public static <T> RxBusEvent<T> newInstance(String tag, T data) {
        return new RxBusEvent<>(tag, data);
    }

    public static <T> RxBusEvent<T> newInstance(String tag, T data, String des) {
        return new RxBusEvent<>(tag, data, des);
    }

    public static <T> RxBusEvent<List<T>> newInstance(String tag, List<T> dataList, String des) {
        return new RxBusEvent<>(tag, dataList, des);
    }

    private long event;
    private String tag;
    private T data;
    private String des;

    private RxBusEvent() {
    }

    private RxBusEvent(String tag, T data) {
        this.event = System.currentTimeMillis();
        this.tag = tag;
        this.data = data;
    }

    private RxBusEvent(String tag, T data, String des) {
        this.event = System.currentTimeMillis();
        this.tag = tag;
        this.data = data;
        this.des = des;
    }

    public long getEvent() {
        return event;
    }

    public void setEvent(long event) {
        this.event = event;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
