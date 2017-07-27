package com.ergou.ATM.dao;

import com.ergou.ATM.po.Details;

import java.util.List;

public interface Server {
    public void openAnAccount(String password) throws Exception;
    public void deposit(String cardNum, int amount) throws Exception;
    public void withdrawls(String cardNum, int amount) throws Exception;
    public void transfer(String cardNum, String targetCardNum, int amount) throws Exception;
    public List<Details> queryDetails(String cardNum) throws Exception;
}

