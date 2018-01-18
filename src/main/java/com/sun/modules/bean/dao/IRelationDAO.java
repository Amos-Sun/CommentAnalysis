package com.sun.modules.bean.dao;

import com.sun.modules.bean.po.RelationPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunguiyong on 2018/1/9.
 */
@Repository(value = "relationDAO")
public interface IRelationDAO {

    void insertRecord(List<RelationPO> list);

    @Select("select comment from relation where cid =#{cid} and user_name=#{userName}")
    String getCommentByCidAndUserNaem(@Param("cid") String cid, @Param("userName") String userName);
}
