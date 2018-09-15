package com.learn.epetShop.service;

import com.learn.epetShop.entity.Pet;
import com.learn.epetShop.entity.PetOwner;
import com.learn.epetShop.entity.PetStore;

import java.util.List;

public interface PetStoreService extends Accountable,Breadable,Buyable,Sellable{
    /**
     * 查询所有库存宠物
     * @param storeId 库存id
     * @return List<Pet> 宠物列表
     */
    public List<Pet> getPetsInstock(long storeId);

    /**
     * 查询所有新培育的宠物
     * @return
     */
    public List<Pet> getPetsBread();

    /**
     * 定价
     * @param pet
     * @return
     */
    public double charge(Pet pet);

    /**
     * 根据主人信息修改宠物信息
     * @param pet 宠物
     * @param petOwner 宠物主人
     * @param petStore 宠物商店
     * @return 被影响的行数
     */
    public int modifyPet(Pet pet, PetOwner petOwner, PetStore petStore);

    /**
     *修改宠物主人的信息
     * @param owner 主人
     * @param pet 宠物
     * @param type 类型
     * @return
     */
    public int modifyOwner(PetOwner owner,Pet pet, int type);

    /**
     * 根据宠物商店Id 查询宠物商店
     * @param id 宠物商店ID
     * @return PetStore 宠物商店
     */
    public PetStore getPetStore(long id);

    /**
     * 修改宠物商店信息
     * @param pet 宠物
     * @param type 类型
     * @return 被影响的行数
     */
    public int modifyStore(Pet pet,int type);

    /**
     * 宠物商店登录
     * @return PetStore 宠物商店
     */
    public PetStore login();

    /**
     * 查询所有宠物主人正在出售的宠物
     * @return List pet
     */
    public List<Pet> getPetSelling();

    /**
     * 查询所有宠物商店
     * @return
     */
    public List<PetStore> getAllStore();
}
