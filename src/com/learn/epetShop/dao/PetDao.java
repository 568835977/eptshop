package com.learn.epetShop.dao;

import com.learn.epetShop.entity.Pet;

import java.util.List;

public interface PetDao {
    /**
     * 查询所有宠物信息
     */
    public abstract List<Pet> getAllPet();

    /**
     *根据已知宠物的信息查询宠物信息
     * @param sql sql语句
     * @param param 参数
     * @return List<Pet> 返回宠物列表参数
     */
    public abstract List<Pet> selectPet(String sql,String[] param);

    /**
     * 更新宠物信息
     * @param sql sql语句
     * @param param 参数
     * @return int
     */
    public abstract int updatePet(String sql,Object[] param);
}
