package com.ergou.ATM.mapper;

import com.ergou.ATM.po.User;
import com.ergou.ATM.po.UserCustom;
import com.ergou.ATM.po.UserQueryVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

public class UserMapperTest {
    SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    @Test
    public void insertUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setCardNum(String.valueOf((int)((Math.random()*9+1)*10000000)));
        user.setPassword("111111");
        user.setBalance(0);
        user.setVersion(0);

        userMapper.insertUser(user);
        System.out.println(user.getId());
//        assertEquals(9, user.getId());
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void updateUserByCardNum() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setCardNum("15478980");
        user.setPassword("123460");
        user.setBalance(51);
        userMapper.updateUserByCardNum(user);

        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void findUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);


        UserCustom userCustom  = new UserCustom();
        userCustom.setId(8);
        UserQueryVo userQueryVo = new UserQueryVo();
        userQueryVo.setUserCustom(userCustom);
        User user = userMapper.findUser(userQueryVo);
        System.out.println(user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void findUserByCardNum() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);



        User user = userMapper.findUserByCardNum("140632626");
        System.out.println(user);
        sqlSession.commit();
        sqlSession.close();
    }



}