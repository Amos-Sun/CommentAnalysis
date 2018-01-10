package com.sun.modules.bean.dao;

import com.sun.modules.bean.po.UserPO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
@Repository(value = "userDAO")
public interface IUserDAO {

    void insertUserInfo(List<UserPO> list);

    @Select("select name from user")
    List<String> getAllName();
}
