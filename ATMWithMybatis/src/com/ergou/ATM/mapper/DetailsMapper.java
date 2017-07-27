package com.ergou.ATM.mapper;

import com.ergou.ATM.po.Details;
import com.ergou.ATM.po.DetailsQueryVo;

import java.util.List;

public interface DetailsMapper {
    public void insertDetails(Details details) throws Exception;
    //支持复杂查询
    public List<Details> findDetails(DetailsQueryVo detailsQueryVo) throws Exception;

    //仅支持简单根据用户账号查询
    public List<Details> findDetailsByCardNum(String cardNum) throws Exception;
}
