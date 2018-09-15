package com.learn.epetShop.service.impl;

import com.learn.epetShop.dao.PetStoreDao;
import com.learn.epetShop.dao.impl.PetStoreDaoImpl;
import com.learn.epetShop.service.PetStoreFactory;

import java.util.Scanner;

/**
 * @program: eptshop
 * @description: 实现类
 * @author: Mr.Xie
 * @create: 2018-09-12 15:28
 **/
public class PetStoreFactoreImpl implements PetStoreFactory {
    @Override
    public void createPetStore() {
        //获取输入的信息
        Scanner input = new Scanner(System.in);
        System.out.println("请在下方输入宠物商店的属性:");
        System.out.println("请输入宠物商店的名字:");
        String storeName = input.nextLine();
        System.out.println("请设置商店密码");
        String storePassword = input.nextLine();
        System.out.println("请输入宠物商店的资金(整数)");
        int storeBalance = input.nextInt();
        String sql = "insert into petStore(name,password,balance) values(?,?,?)";
        Object[] storeParam = {storeName,storePassword,storeBalance};
        PetStoreDao petStoreDao = new PetStoreDaoImpl();
        //执行sql语句,返回影响的行数
        int count = petStoreDao.updateStore(sql,storeParam);
        //当影响的行数大于0,则提示,成功创建了宠物商店
        if (count>0){
            System.out.println("成功创建了一个宠物商店,商店名字叫:"+ storeName);
        }
    }
}
