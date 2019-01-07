package com.face.facepay.dao;

import com.face.facepay.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {
    /**
     * 根据用户名查询用户信息,只有用户名和余额
     * @param username
     * @return
     */
     User getUser(String username);

    /**
     * 根据用户名查询密码，登录验证时使用
     * @param username
     * @return
     */
     String getPassword(String username);

    /**
     * 插入一台用户记录，注册时使用
     * @param user
     * @return
     */
     Boolean insertUser(User user);

    /**
     * 根据用户名修改用户余额
     * @param username
     * @return
     */
     Boolean updateUserMoneyByUsername(@Param("username") String username,@Param("balance") double balance);

    /**
     * 根据用户名查找余额
     * @param username
     * @return
     */
     Double getBalanceByUsername(String username);

    /**
     * 用户充钱
     * @param username
     * @param balance
     * @return
     */
     Boolean addUserMoneyByUsername(@Param("username") String username,@Param("balance") double balance);

    /**
     * 查询用户列表
     * @return
     */
     List<User> queryUser();

    /**
     * 修改用户角色权限
     * @param user
     * @return
     */
    Boolean updateUserRoleByUsername(User user);
}
