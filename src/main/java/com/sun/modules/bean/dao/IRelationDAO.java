package com.sun.modules.bean.dao;

import com.sun.modules.bean.po.RelationPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunguiyong on 2018/1/9.
 */
@Repository(value = "relationDAO")
public interface IRelationDAO {

    void insertRecord(List<RelationPO> list);
}
