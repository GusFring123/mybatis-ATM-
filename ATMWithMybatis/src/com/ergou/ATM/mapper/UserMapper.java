package com.ergou.ATM.mapper;

import com.ergou.ATM.po.User;
import com.ergou.ATM.po.UserQueryVo;

public interface UserMapper {
    public void insertUser(User user) throws Exception;

    public int updateUserByCardNum(User user) throws Exception;

    //支持复杂查询
    public User findUser(UserQueryVo userQueryVo) throws Exception;

    ////仅支持简单根据用户账号查询
    public User findUserByCardNum(String cardNum) throws Exception;

}
