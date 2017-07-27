package com.ergou.ATM.po;

import java.util.Date;

public class User {
    private int id;
    private String cardNum;
    private String password;
    private int balance;
    private Date createTime;
    private Date modifyTime;
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cardNum='" + cardNum + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", version=" + version +
                '}';
    }
}
