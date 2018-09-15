package com.learn.epetShop.service.impl;

import com.learn.epetShop.dao.PetDao;
import com.learn.epetShop.dao.PetOwnerDao;
import com.learn.epetShop.dao.impl.AccountDaoImpl;
import com.learn.epetShop.dao.impl.PetDaoImpl;
import com.learn.epetShop.dao.impl.PetOwnerDaoImpl;
import com.learn.epetShop.dao.impl.PetStoreDaoImpl;
import com.learn.epetShop.entity.Pet;
import com.learn.epetShop.entity.PetOwner;
import com.learn.epetShop.entity.PetStore;
import com.learn.epetShop.service.PetOwnerService;
import com.learn.epetShop.service.PetStoreService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @program: eptshop
 * @description: 宠物主人实现类
 * @author: Mr.Xie
 * @create: 2018-09-12 15:08
 **/
public class PetOwnerServiceImpl implements PetOwnerService {
    @Override
    /**
     * 宠物主人登录
     */
    public PetOwner login() {
        Scanner input = new Scanner(System.in);
        System.out.println("请先登录,请输入主人账户的用户名:");
        String ownerName = input.nextLine().trim();
        System.out.println("请输出主人账户的密码:");
        String ownerPassword = input.nextLine().trim();
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();
        String sql = "select * from petowner where name=? and password=?";
        String[] param = {ownerName,ownerPassword};
        PetOwner petOwner = petOwnerDao.selectOwner(sql,param);
        if (petOwner!=null){
            System.out.println("---------恭喜您登录成功---------");
            System.out.println("---------您的基本信息-----------");
            System.out.println("名称:"+petOwner.getName());
            System.out.println("元宝数:"+petOwner.getMoney());
        }
        else {
            System.out.println("登录失败!");
        }
        return petOwner;
    }

    @Override
    /**
     * 根据主人Id查询主人名下的所有宠物信息
     */
    public List<Pet> getMyPet(int ownerId) {
        List<Pet> petList = new ArrayList<>();
        String sql = "select * from pet where owner_id=?";
        String[] param = {String.valueOf(ownerId)};
        PetDao petDao = new PetDaoImpl();
        petList = petDao.selectPet(sql,param);
        return petList;
    }

    @Override
    /**
     * 宠物主人购买库存宠物,根据页面获得的序号,来购买库存宠物或新培育宠物
     */
    public void buy(Pet pet) {
        String sql = "select * from petOwner where id=?";
        //获取该宠物的主人ID的值,并将其转换成字符串
        String param[] = {String.valueOf(pet.getOwnerId())};
        //多态
        PetOwnerDao ownerDao = new PetOwnerDaoImpl();

        //获取主人的信息
        PetOwner owner = ownerDao.selectOwner(sql,param);
        PetStoreService petStore = new PetStoreServiceImpl();
        int updatePet = petStore.modifyPet(pet,owner,null);
        if (updatePet>0){
            //主人买宠物,type为0
            int updateOwner = petStore.modifyOwner(owner,pet,0);
            if (updateOwner>0){
               int  updatePetStore = petStore.modifyStore(pet,0);
               if (updatePetStore>0){
                   int inserAccount = petStore.modifyAccount(pet,owner);
                   if (inserAccount>0){
                       System.out.println("台账正确插入一条信息");
                   }
               }
            }
        }

    }

    @Override
    /**
     * 宠物主人向宠物商店出售宠物
     */
    public void sell(Pet pet) {
        PetDaoImpl petDao = new PetDaoImpl();
        PetOwnerDaoImpl ownerDao = new PetOwnerDaoImpl();
        String updatesql = "update pet set owner_id = null where id=?";
        Object[] param = {pet.getId()};
        //更新宠物信息
        int updatePet =  petDao.executeSQL(updatesql,param);
        //更新宠物主人信息
        if (updatePet>0){
            //查询宠物主人资料
            String ownersql = "select * from petowner where id=?";
            //查询出售宠物所对应的主人ID
            String ownerparam[] = {String.valueOf(pet.getOwnerId())};
            //根据sql语句和主人id查询到主人对象
            PetOwner owner = ownerDao.selectOwner(ownersql,ownerparam);

            String updateOwnerSql = "update petowner set money=? where id=?";
            Object[] ownerParam = {owner.getMoney()+3,owner.getId()};
            //执行sql语句,更新主人资料
            int updateOwner = ownerDao.executeSQL(updateOwnerSql,ownerParam);
            //更新宠物商店的信息
            if (updateOwner>0){
                PetStoreServiceImpl store = new PetStoreServiceImpl();
                PetStore petStore = store.getPetStore(pet.getStoreId());

                String updateStore = "update petStore set balance=? where id=?";
                Object[] storeparam = {petStore.getBalance()-3,petStore.getId()};
                PetStoreDaoImpl storeDao = new PetStoreDaoImpl();
                int updatestore = storeDao.executeSQL(updateStore,storeparam);
                //更新宠物商店台账信息
                if (updatestore>0){
                    String insertSql = "insert into account(deal_type,pet_id,seller_id,buyer_id,price,deal_time) values (?,?,?,?,?,?)";
                    //将日期格式化
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String time = sdf.format(date);

                    Object[] accountParam = {2,pet.getId(),owner.getId(),pet.getStoreId(),3,time};
                    AccountDaoImpl accountDao = new AccountDaoImpl();
                    int insertAccount = accountDao.updateAccount(insertSql,accountParam);
                    if (insertAccount>0){
                        System.out.println("台账正确插入了一条信息");
                    }
                }
            }

        }

    }
    @Override
    public List<PetOwner> getAllOwner(){
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();
        List<PetOwner> ownerList = petOwnerDao.getAllOwner();
        return ownerList;
    }
}
