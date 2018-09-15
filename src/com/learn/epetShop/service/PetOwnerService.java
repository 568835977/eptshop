package com.learn.epetShop.service;

import com.learn.epetShop.entity.Pet;
import com.learn.epetShop.entity.PetOwner;

import java.util.List;

/**
 * 宠物主人接口
 */
public interface PetOwnerService extends Sellable,Buyable{
    /**
     * 宠物主人登录
     * @return PetOwner 宠物主人
     */
    public PetOwner login();

    /**
     * 根据宠物主人id 获取该宠物主人的所有宠物信息
     * @param ownerId 主人id
     * @return List<Pet>
     */
    public List<Pet> getMyPet(int ownerId);

    /**
     * 获取所有主人列表
     * @return
     */
    public List<PetOwner> getAllOwner();
}
