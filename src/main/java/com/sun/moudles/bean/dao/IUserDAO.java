package com.sun.moudles.bean.dao;

import com.sun.moudles.bean.domain.UserDO;
import com.sun.moudles.bean.po.UserPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
@Repository
public interface IUserDAO {

    void insertUserInfo(List<UserPO> list);
}
