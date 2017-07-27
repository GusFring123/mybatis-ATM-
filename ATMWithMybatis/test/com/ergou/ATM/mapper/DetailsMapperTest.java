package com.ergou.ATM.mapper;

import com.ergou.ATM.po.Details;
import com.ergou.ATM.po.DetailsCustom;
import com.ergou.ATM.po.DetailsQueryVo;
import com.ergou.ATM.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resources;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class DetailsMapperTest {
    SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = org.apache.ibatis.io.Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void insertDetails() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Details details = new Details();
        details.setCardNum("140632626");
        details.setAmount(500);
        details.setOperationType(1);
        details.setRemarks("deposit");
        DetailsMapper detailsMapper = sqlSession.getMapper(DetailsMapper.class);
        detailsMapper.insertDetails(details);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void findDetails() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DetailsCustom details = new DetailsCustom();
        details.setCardNum("140632626");
        DetailsQueryVo detailsQueryVo = new DetailsQueryVo();
        detailsQueryVo.setDetailsCustom(details);
        DetailsMapper detailsMapper = sqlSession.getMapper(DetailsMapper.class);
        List list = detailsMapper.findDetails(detailsQueryVo);
        System.out.println(list);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void findDetailsByCardNum() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DetailsMapper detailsMapper = sqlSession.getMapper(DetailsMapper.class);

        List<Details> details = detailsMapper.findDetailsByCardNum("140632626");
        System.out.println(details);
        sqlSession.commit();
        sqlSession.close();

    }
}