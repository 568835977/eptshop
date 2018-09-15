package com.learn.epetShop.dao;

import com.learn.epetShop.entity.Account;

import java.util.List;

/**
 * AccountDao接口
 */
public interface AccountDao {
    /**
     * 更新台账信息
     */
    public abstract int updateAccount(String sql, Object[] param);

    /**
     * 根据查询条件查询宠物商店账单
     * @param sql sql语句
     * @param param param数组
     * @return List<Account>  返回Account列表
     */
    public abstract List<Account> getPetStoreAccount(String sql, String[] param);
}
