package com.dpk.wgj.bean;

import java.io.Serializable;

public class MonthInfo implements Serializable {
    private int January;
    private int February;
    private int March;
    private int April;
    private int May;
    private int June;
    private int July;
    private int August;
    private int September;
    private int October;
    private int November;
    private int December;

    public int getJanuary() {
        return January;
    }

    public void setJanuary(int january) {
        January = january;
    }

    public int getFebruary() {
        return February;
    }

    public void setFebruary(int february) {
        February = february;
    }

    public int getMarch() {
        return March;
    }

    public void setMarch(int march) {
        March = march;
    }

    public int getApril() {
        return April;
    }

    public void setApril(int april) {
        April = april;
    }

    public int getMay() {
        return May;
    }

    public void setMay(int may) {
        May = may;
    }

    public int getJune() {
        return June;
    }

    public void setJune(int june) {
        June = june;
    }

    public int getJuly() {
        return July;
    }

    public void setJuly(int july) {
        July = july;
    }

    public int getAugust() {
        return August;
    }

    public void setAugust(int august) {
        August = august;
    }

    public int getSeptember() {
        return September;
    }

    public void setSeptember(int september) {
        September = september;
    }

    public int getOctober() {
        return October;
    }

    public void setOctober(int october) {
        October = october;
    }

    public int getNovember() {
        return November;
    }

    public void setNovember(int november) {
        November = november;
    }

    public int getDecember() {
        return December;
    }

    public void setDecember(int december) {
        December = december;
    }
    @Override

    public String toString() {
        return "MonthInfo{" +
                "January=" + January +
                ", February='" + February + '\'' +
                ", March='" + March + '\'' +
                ", April='" + April + '\'' +
                ", May='" + May + '\'' +
                ", June=" + June +
                ", July='" + July + '\'' +
                ", August='" + August + '\'' +
                ", September=" + September +
                ", October=" + October +
                ", November=" + November +
                ", December=" + December +
                '}';
    }
}
