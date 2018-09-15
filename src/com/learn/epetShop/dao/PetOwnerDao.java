package com.learn.epetShop.dao;

import com.learn.epetShop.entity.PetOwner;

import java.util.List;

public interface PetOwnerDao {
    /**
     * 查询所有宠物主人的信息
     * @return List<PetOwner>
     */
    public abstract List<PetOwner> getAllOwner();

    /**
     * 更新宠物主人信息
     * @param sql sql语句
     * @param param 参数数组
     * @return int 返回影响的行数
     */
    public abstract int updateOwner(String sql,Object[] param);

    /**
     * 根据查询条件查询宠物主人信息
     * @param sql sql语句
     * @param param 参数数组
     * @return PetOwner
     */
    public abstract PetOwner selectOwner(String sql,String[] param);
}
