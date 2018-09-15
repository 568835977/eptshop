package com.learn.epetShop.dao;

import com.learn.epetShop.entity.PetStore;

import java.util.List;

public interface PetStoreDao {
    /**
     * 查询所有宠物商店
     * @return List<PetStore>
     */
    public abstract List<PetStore> getAllStore();

    /**
     * 根据查询条件查询出宠物商店
     * @param sql sql语句
     * @param param 参数数组
     * @return PetStore
     */
    public PetStore getPetStore(String sql,String[] param);

    /**
     * 更新宠物商店信息
     * @param sql sql语句
     * @param param 参数数组
     * @return int 影响的行数
     */
    public int updateStore(String sql, Object[] param);
}
