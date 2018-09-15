package com.learn.epetShop.service;

import com.learn.epetShop.entity.Account;
import com.learn.epetShop.entity.Pet;
import com.learn.epetShop.entity.PetOwner;

import java.util.List;

/**
 * 宠物商店台账接口
 * @author xkc
 */
public interface Accountable {
    /**
     * 查询宠物商店台账信息
     * @param storeId
     * @return
     */
    public List<Account> account(long storeId);

    /**
     * 修改宠物商店台账信息
     * @param pet 宠物
     * @param owner 主人
     * @return 影响的行数
     */
    public  int modifyAccount(Pet pet, PetOwner owner);

}
