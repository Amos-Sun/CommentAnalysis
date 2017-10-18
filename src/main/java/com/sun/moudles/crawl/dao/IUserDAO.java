package com.sun.moudles.crawl.dao;

import com.sun.moudles.crawl.domain.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunguiyong on 2017/10/17.
 */
@Repository
public interface IUserDAO {

    void insertUserInfo(List<UserDO> list);
}
