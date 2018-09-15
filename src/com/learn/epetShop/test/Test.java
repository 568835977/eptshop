package com.learn.epetShop.test;

import com.learn.epetShop.dao.PetDao;
import com.learn.epetShop.dao.PetOwnerDao;
import com.learn.epetShop.dao.PetStoreDao;
import com.learn.epetShop.dao.impl.PetDaoImpl;
import com.learn.epetShop.dao.impl.PetOwnerDaoImpl;
import com.learn.epetShop.dao.impl.PetStoreDaoImpl;
import com.learn.epetShop.entity.Account;
import com.learn.epetShop.entity.Pet;
import com.learn.epetShop.entity.PetOwner;
import com.learn.epetShop.entity.PetStore;
import com.learn.epetShop.service.PetOwnerService;
import com.learn.epetShop.service.PetStoreFactory;
import com.learn.epetShop.service.PetStoreService;
import com.learn.epetShop.service.impl.*;

import java.util.List;
import java.util.Scanner;

/**
 * @program: eptshop
 * @description: 测试类
 * @author: Mr.Xie
 * @create: 2018-09-13 14:48
 **/
public class Test {
    public static void main(String[] args) {
        startPetShop();
    }

    public static void startPetShop() {
        System.out.println("宠物商店启动");
        System.out.println("***********************");
        //输出宠物信息
        System.out.println("所有宠物信息:");
        PetDao petDao = new PetDaoImpl();
        //获取宠物列表
        List<Pet> petList = petDao.getAllPet();
        System.out.println("序号  " + "宠物名称");
        for (int i = 0; i < petList.size(); i++) {
            Pet pet = petList.get(i);
            System.out.println(i + 1 + "    " + pet.getName());
        }
        System.out.println("***********************");
        //输出主人信息
        System.out.println("所有主人信息");
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();
        List<PetOwner> petOwnerList = petOwnerDao.getAllOwner();
        System.out.println("序号  " + "主人名称");
        for (int i = 0; i < petOwnerList.size(); i++) {
            PetOwner petOwner = petOwnerList.get(i);
            System.out.println(i + 1 + "    " + petOwner.getName());
        }
        System.out.println("***********************");
        //输出宠物商店信息
        System.out.println("所有宠物商店信息");
        PetStoreDao petStoreDao = new PetStoreDaoImpl();
        List<PetStore> petStoreList = petStoreDao.getAllStore();
        System.out.println("序号  " + "主人名称");
        for (int i = 0; i < petStoreList.size(); i++) {
            PetStore petStore = petStoreList.get(i);
            System.out.println(i + 1 + "    " + petStore.getName());
        }
        System.out.println("***********************");
        Scanner input = new Scanner(System.in);
        System.out.println("请输入登录模式,1:主人登录,2:商店登录");
        boolean type = true;
        String num;
        while (type) {
            num = input.nextLine();
            if ("1".equals(num)) {
                Test.ownerLogin();
                type = false;
            }
            if ("2".equals(num)) {
                Test.storeLogin();
                type = false;
            }
        }
    }

    public static PetStore storeLogin() {
        Scanner input = new Scanner(System.in);
        PetStoreService petStoreService = new PetStoreServiceImpl();
        //登录商店,返回petStore对象
        PetStore petStore = petStoreService.login();
        System.out.println("登录成功,可以进行如下操作");
        System.out.println("1.购买宠物");
        System.out.println("2.出售宠物");
        System.out.println("3.培育宠物");
        System.out.println("4.查询待售宠物");
        System.out.println("5.查看商店结余");
        System.out.println("6.查看商店账目");
        System.out.println("7.新开宠物商店");
        System.out.println("请根据需要,输入相应的序号,退出按输入0");
        boolean status = true;
        while (status) {
            int num = input.nextInt();
            switch (num) {
                case 0:
                    System.out.println("退出成功");
                    status = false;
                    break;
                case 1:
                    Test.storeBuy();
                    status = false;
                    break;
                case 2:
                    Test.storeSell(petStore);
                    status = false;
                    break;
                case 3:
                    Test.storeBread();
                    status = false;
                    break;
                case 4:
                    Test.queryPetStock(petStore.getId());
                    status = false;
                    break;
                case 5:
                    Test.queryStoreBalance(petStore);
                    status = false;
                    break;
                case 6:
                    Test.getAccount(petStore.getId());
                    status = false;
                    break;
                case 7:
                    Test.createPetStore();
                    status = false;
                    break;
                default:
            }
        }
        return null;
    }

    /**
     * 查询待售宠物
     *
     * @param storeId
     */
    public static void queryPetStock(long storeId) {
        PetStoreService petStoreService = new PetStoreServiceImpl();
        List<Pet> petList = petStoreService.getPetsInstock(storeId);
        Pet pet = null;
        System.out.println("序号\t" + "宠物名称\t" + "宠物类型");
        for (int i = 0; i < petList.size(); i++) {
            pet = petList.get(i);
            System.out.println((i + 1) + "\t" + pet.getName() + "\t" + pet.getTypeName());
        }
    }

    public static PetOwner ownerLogin() {
        Scanner input = new Scanner(System.in);
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        PetOwner petOwner = petOwnerService.login();
        boolean reg = true;
        while (reg) {
            if (petOwner == null) {
                System.out.println("登录失败");
                petOwnerService.login();
                reg = true;
            } else {
                reg = false;
                System.out.println("登录成功,您可以购买和卖出宠物:");
                System.out.println("1:购买宠物");
                System.out.println("2:出售宠物");
                boolean type = true;
                while (type) {
                    int num = input.nextInt();
                    if (1 == num) {
                        Test.ownerBuy(petOwner);
                        type = false;
                    } else if (2 == num) {
                        Test.ownerSell(petOwner);
                        type = false;
                    } else {
                        System.out.println("输入的值有误,请重新输入");
                        type = true;
                    }
                }
            }
        }
        return petOwner;
    }

    /**
     * 主人购买宠物
     *
     * @param owner
     */
    public static void ownerBuy(PetOwner owner) {
        PetStoreService petStoreService = new PetStoreServiceImpl();
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        Scanner input = new Scanner(System.in);
        System.out.println("----请输入要购买范围:--------");
        System.out.println("1:购买库存宠物");
        System.out.println("2:购买新培育宠物");
        //定义局部变量
        Pet pet = null;
        List<Pet> petList = null;
        boolean type = true;
        while (type) {
            int num = input.nextInt();
            //购买库存宠物
            if (1 == num) {
                System.out.println("---以下是库存宠物----");
                System.out.println("序号  " + "宠物名称 " + "类型   " + "元宝数  ");
                petList = petStoreService.getPetsInstock(0);
                for (int i = 0; i < petList.size(); i++) {
                    pet = petList.get(i);
                    double price = petStoreService.charge(pet);
                    System.out.println((i + 1) + "    " + pet.getName() + "   " + pet.getTypeName() + "   " + price);
                }
                System.out.println("请选择需要购买哪个宠物,并输入序号");
                num = input.nextInt();
                pet = petList.get(num - 1);
                //将宠物的主人赋值
                pet.setOwnerId(owner.getId());
                //调用购买宠物
                petOwnerService.buy(pet);

                type = false;
            }
            //购买培育宠物
            else if (2 == num) {
                System.out.println("---以下是新培育宠物----");
                System.out.println("序号  " + "宠物名称 " + "类型   " + "元宝数  ");
                petList = petStoreService.getPetsBread();
                for (int i = 0; i < petList.size(); i++) {
                    pet = petList.get(i);
                    double price = petStoreService.charge(pet);
                    System.out.println((i + 1) + "\t" + pet.getName() + "\t" + pet.getTypeName() + "\t" + price);
                }
                System.out.println("请选择需要购买哪个宠物,并输入序号");
                String count = input.next();
                if (count.matches("[0-9]*")) {
                    num = Integer.parseInt(count);
                    pet = petList.get(num - 1);
                    //将宠物的主人赋值
                    pet.setOwnerId(owner.getId());
                    //调用主人购买宠物,修改相关表
                    petOwnerService.buy(pet);
                }
                type = false;

            } else {
                System.out.println("您输入的值有误,请重新输入!");
                type = true;
            }
        }

    }

    /**
     * 查询商店结余
     *
     * @param petStore
     */
    public static void queryStoreBalance(PetStore petStore) {
        double balance = petStore.getBalance();
        System.out.println(petStore.getName() + "\t" + "结余为:" + balance);

    }

    //查看商店账目
    public static void getAccount(long storeId) {
        PetStoreService storeService = new PetStoreServiceImpl();
        List<Account> accountList = storeService.account(storeId);
        for (int i = 0; i < accountList.size(); i++) {
            Account account = accountList.get(i);
            String type = null;
            if (account.getDealType() == 1) {
                type = "商店卖宠物给主人";
            }
            if (account.getDealType() == 2) {
                type = "主人卖给商店";
            }
            System.out.println("第" + (i + 1) + "笔交易" + "交易类型为:" + type + "交易金额是:" + account.getPrice() + "个元宝");
        }
    }

    /**
     * 创建宠物商店
     */
    public static void createPetStore() {
        PetStoreFactory petStoreFactory = new PetStoreFactoreImpl();
        petStoreFactory.createPetStore();
    }

    public static void storeBread() {
        PetStoreService petStoreService = new PetStoreServiceImpl();
        Scanner input = new Scanner(System.in);
        System.out.println("请输入宠物类型");
        String type = input.nextLine();
        petStoreService.bread(type);
    }

    /**
     * 宠物主人出售宠物
     */
    public static void ownerSell(PetOwner petOwner) {
        Scanner input = new Scanner(System.in);
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        System.out.println("-----我的宠物列表-----");
        List<Pet> petList = petOwnerService.getMyPet(petOwner.getId());
        System.out.println("序号\t宠物名称\t类型\t");
        for (int i = 0; i < petList.size(); i++) {
            Pet pet = petList.get(i);
            System.out.println((i + 1) + "\t" + pet.getName() + "\t" + pet.getTypeName());
        }
        System.out.println("请选择要出售的宠物序号");
        boolean type = true;
        while (type) {
            int num = input.nextInt();
            if (num - 1 < petList.size() && (num - 1) >= 0) {
                Pet pet = petList.get(num - 1);
                System.out.println("您要售卖的宠物信息如下:");
                System.out.println(pet.getName() + "\t" + pet.getTypeName());
                System.out.println("请确认是否卖出,y代表卖出,n代表不卖");
                String code = input.next();
                if (code != null) {
                    if ("y".equals(code)) {
                        System.out.println("下面是宠物商店的,请选择您要出售到的商店序号:");
                        PetStoreService petStoreService = new PetStoreServiceImpl();
                        List<PetStore> petStoreList = petStoreService.getAllStore();
                        PetStore petStore = null;
                        System.out.println("序号\t宠物商店\t");
                        for (int i = 0; i < petStoreList.size(); i++) {
                            petStore = petStoreList.get(i);
                            System.out.println((i + 1) + "\t" + petStore.getName());
                        }
                        num = input.nextInt();
                        if ((num - 1) < petList.size() && (num - 1) >= 0) {
                            petStore = petStoreList.get(num - 1);
                        }
                        pet.setStoreId(petStore.getId());
                        petOwnerService.sell(pet);
                    } else if ("n".equals(code)) {
                        System.out.println("您放弃本次交易");
                    } else {
                        System.out.println("您的输入有误");
                    }
                }
                type = true;
            } else {
                System.out.println("输入有误,请按照序号重新输入");
                type = false;
            }
            type = false;
        }
    }

    /**
     * 宠物商店出售宠物
     */
    public static void storeSell(PetStore petStore) {
        Scanner input = new Scanner(System.in);
        PetStoreService petStoreService = new PetStoreServiceImpl();
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        List<Pet> petList = petStoreService.getPetsInstock(petStore.getId());
        Pet pet = null;
        System.out.println("以下是宠物商店正在出售的宠物");
        System.out.println("序号\t宠物名称\t类型\t");
        for (int i = 0; i < petList.size(); i++) {
            pet = petList.get(i);
            System.out.println((i + 1) + "\t" + pet.getName() + "\t" + pet.getTypeName());
        }
        System.out.println("-----请选择需要出售的宠物序号-----");
        boolean status = true;
        while (status) {
            int num = input.nextInt();
            if ((num - 1) < petList.size() && (num - 1) >= 0) {
                System.out.println("要出售的宠物信息如下");
                pet = petList.get(num - 1);
                System.out.println("宠物名:\t" + pet.getName() + "\t宠物类别:" + pet.getTypeName());
                System.out.println("请确认是否出售,y代表卖,n代表不卖");
                String code = input.next();
                if (code != null) {
                    //当输入的值为y时
                    if ("y".equals(code)) {
                        System.out.println("以下是现有宠物主人买家,请输入序号选择");
                        //获取所有宠物买家
                        List<PetOwner> petOwnerList = petOwnerService.getAllOwner();
                        PetOwner petOwner = null;
                        System.out.println("序号\t" + "主人名称:");
                        for (int i = 0; i < petOwnerList.size(); i++) {
                            petOwner = petOwnerList.get(i);
                            System.out.println((i + 1) + "\t" + petOwner.getName());
                        }
                        num = input.nextInt();
                        //出售宠物
                        //1.将宠物的主人赋值 2.执行sell方法
                        if ((num - 1) < petOwnerList.size() && (num - 1) >= 0) {
                            petOwner = petOwnerList.get(num - 1);
                        }
                        pet.setOwnerId(petOwner.getId());
                        petStoreService.sell(pet);

                        status = false;
                    }
                    //输入的值为n
                    else if ("n".equals(code)) {
                        System.out.println("您放弃了本次交易");
                        status = false;
                    }
                    //输入的是其他值
                    else {
                        System.out.println("您的输入有误");
                        status = false;
                    }
                }
            } else {
                System.out.println("输入有误,请按照序号重新输入");
                status = true;
            }
            //退出系统
            status = false;
        }
    }

    /**
     * 商店购买宠物
     */
    public static void storeBuy() {
        Scanner input = new Scanner(System.in);
        PetStoreService petStoreService = new PetStoreServiceImpl();
        System.out.println("-----以下是宠物主人正在出售的宠物-----");
        List<Pet> petList = petStoreService.getPetSelling();
        Pet pet = null;
        System.out.println("序号\t宠物名称\t类型\t元宝数");
        for (int i = 0; i < petList.size(); i++) {
            pet = petList.get(i);
            double price = petStoreService.charge(pet);
            System.out.println((i + 1) + "\t" + pet.getName() + "\t" + pet.getTypeName() + "\t" + price);
        }
        System.out.println("-----请选择要购买的宠物序号-----");
        int num = input.nextInt();
        pet = petList.get(num - 1);
        petStoreService.buy(pet);

    }
}
