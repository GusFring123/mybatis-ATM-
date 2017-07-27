package com.ergou.ATM.dao;

import com.ergou.ATM.mapper.DetailsMapper;
import com.ergou.ATM.mapper.UserMapper;
import com.ergou.ATM.po.Details;
import com.ergou.ATM.po.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
//这是基本的业务逻辑代码，提供五个功能，为 开户，存款，取款，转账，查看明细；具体的用户登录，与密码锁需要结合不同的交互界面进行具体的改写这里只提供基本功能
public class ServerImpl implements Server {
    static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        try {
            inputStream = org.apache.ibatis.io.Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public void openAnAccount(String password) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = new User();
        //随机生成账户
        user.setCardNum(String.valueOf((int) ((Math.random() * 9 + 1) * 10000000)));

        //获取密码，写入账户
        user.setPassword(password);
        user.setBalance(0);
        user.setVersion(0);

        //插入用户
        userMapper.insertUser(user);
        //得到用户id
        System.out.println(user.getId());

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public void deposit(String cardNum, int amount) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        DetailsMapper detailsMapper = sqlSession.getMapper(DetailsMapper.class);
        //检查账户是否存在，与核实金额是否大于零
        User user = userMapper.findUserByCardNum(cardNum);
        if (user != null && amount > 0) {
            User user1 = new User();
            user1.setBalance(amount + user.getBalance());
            userMapper.updateUserByCardNum(user1);

            //增加流水

            Details details = new Details();
            details.setCardNum(cardNum);
            details.setAmount(amount);
            details.setOperationType(1);
            details.setRemarks("存款");
            detailsMapper.insertDetails(details);

        } else {
            System.out.println("存款失败");
        }

        sqlSession.commit();
        sqlSession.close();

    }

    @Override
    public void withdrawls(String cardNum, int amount) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        DetailsMapper detailsMapper = sqlSession.getMapper(DetailsMapper.class);

        User user = userMapper.findUserByCardNum(cardNum);
        if (user != null && amount > 0 && amount < user.getBalance()) {
            User tempUser = new User();
            tempUser.setCardNum(cardNum);
            tempUser.setBalance(-amount + user.getBalance());
            tempUser.setVersion(user.getVersion() + 1);
            userMapper.updateUserByCardNum(tempUser);

            //增加流水

            Details details = new Details();
            details.setCardNum(cardNum);
            details.setAmount(amount);
            details.setOperationType(2);
            details.setRemarks("qu款");
            detailsMapper.insertDetails(details);
        } else {
            System.out.println("qu款失败");
        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void transfer(String cardNum, String targetCardNum, int amount) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        DetailsMapper detailsMapper = sqlSession.getMapper(DetailsMapper.class);

        User user = userMapper.findUserByCardNum(cardNum);
        User targetUser = userMapper.findUserByCardNum(targetCardNum);
        //判断双方账户是否存在，转账金额是否大于零且小余额
        if (user != null && targetUser!= null && amount > 0 && amount < user.getBalance()) {

            User tempUser = new User();
            tempUser.setCardNum(cardNum);
            tempUser.setBalance(-amount + user.getBalance());
            tempUser.setVersion(user.getVersion() + 1);

            userMapper.updateUserByCardNum(tempUser);


            //增加流水

            Details details = new Details();
            details.setCardNum(cardNum);
            details.setAmount(amount);
            details.setOperationType(3);
            details.setRemarks("zhuanchu");
            detailsMapper.insertDetails(details);

            tempUser.setCardNum(targetCardNum);
            tempUser.setBalance(targetUser.getBalance() + amount);
            tempUser.setVersion(targetUser.getVersion() + 1);

            userMapper.updateUserByCardNum(tempUser);

            //增加流水

            Details details1 = new Details();
            details.setCardNum(targetCardNum);
            details.setAmount(amount);
            details.setOperationType(3);
            details.setRemarks("zhuanru");
            detailsMapper.insertDetails(details1);


        }

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<Details> queryDetails(String cardNum) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        DetailsMapper detailsMapper = sqlSession.getMapper(DetailsMapper.class);

        List<Details> details = null;
        //判断账户是否存在
        if (userMapper.findUserByCardNum(cardNum) != null){
           details = detailsMapper.findDetailsByCardNum(cardNum);
        }

        sqlSession.commit();
        sqlSession.close();

        return details;
    }
}



