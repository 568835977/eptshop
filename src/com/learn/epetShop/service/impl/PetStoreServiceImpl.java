package com.learn.epetShop.service.impl;

import com.learn.epetShop.dao.AccountDao;
import com.learn.epetShop.dao.PetDao;
import com.learn.epetShop.dao.PetOwnerDao;
import com.learn.epetShop.dao.PetStoreDao;
import com.learn.epetShop.dao.impl.AccountDaoImpl;
import com.learn.epetShop.dao.impl.PetDaoImpl;
import com.learn.epetShop.dao.impl.PetOwnerDaoImpl;
import com.learn.epetShop.dao.impl.PetStoreDaoImpl;
import com.learn.epetShop.entity.Account;
import com.learn.epetShop.entity.Pet;
import com.learn.epetShop.entity.PetOwner;
import com.learn.epetShop.entity.PetStore;
import com.learn.epetShop.service.PetFactory;
import com.learn.epetShop.service.PetStoreService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @program: eptshop
 * @description: 宠物商店实现类
 * @author: Mr.Xie
 * @create: 2018-09-12 15:28
 **/
public class PetStoreServiceImpl implements PetStoreService {
    @Override
    /**
     * 查询所有或单个库存宠物,由storeId决定 当id=0 则查询所有宠物
     */
    public List<Pet> getPetsInstock(long storeId) {
        PetDao petDao = new PetDaoImpl();
        //将整型storeId转换成字符串类型存储
        String[] param = {String.valueOf(storeId)};
        String sql = "";
        //当storeId!=0时,查询指定storeId的商店库存宠物
        if (storeId != 0) {
            sql = "select * from pet where owner_id is null and store_id=?";
        }
        if (storeId == 0) {
            sql = "select * from pet where owner_id is null";
            param = null;
        }
        List<Pet> petList = petDao.selectPet(sql, param);
        return petList;
    }

    @Override
    /**
     * 查询所有新培育的宠物
     */
    public List<Pet> getPetsBread() {
        PetDao petDao = new PetDaoImpl();
        String sql = "select * from pet where owner_id is null and typename not in(?,?)";
        String[] petParam = {"dog", "penguin"};
        List<Pet> petList = petDao.selectPet(sql, petParam);
        return petList;
    }

    @Override
    /**
     * 给宠物定价
     */
    public double charge(Pet pet) {
        //编写定价规则,按照年龄划分价格
        Date date = new Date();
        double price = 0;
        //获取宠物的年龄
        int age = date.getYear() - pet.getBirthday().getYear();
        //年龄>5则定价3;年龄<5则定价5;
        if (age > 5) {
            price = 3;
        } else {
            price = 5;
        }
        return price;
    }

    @Override
    /**
     * 根据宠物主人信息修改宠物信息,根据PetOwnerEntity和PetStoreEntity的值判断是主人买宠物还是商店买宠物
     * PetOwnerEntity=null是商店买宠物,PetStoreEntity=null是主人买宠物
     */
    public int modifyPet(Pet pet, PetOwner petOwner, PetStore petStore) {
        String updateSql = null;
        long id = 0;
        //主人买宠物
        if (petStore == null) {
            updateSql = "update pet set owner_id=? where id=?";
            id = petOwner.getId();
        }
        //商店买宠物
        else if (petOwner == null) {
            updateSql = "update pet set store_id=?,owner_id=null where id=?";
            id = petStore.getId();
        }
        Object[] param = {id, pet.getId()};
        PetDao petDao = new PetDaoImpl();
        int updatePet = petDao.updatePet(updateSql, param);
        return updatePet;
    }

    @Override
    /**
     * 修改主人信息 type=0 是主人买宠物,type=1 是商店买宠物
     */
    public int modifyOwner(PetOwner owner, Pet pet, int type) {
        PetOwnerDao ownerDao = new PetOwnerDaoImpl();
        String updateOwnerSql = "update petOwner set money=? where id=?";
        double price = this.charge(pet);
        double count = 0;
        if (type == 0) {
            count = (owner.getMoney() - price);
        }
        if (type == 1) {
            count = (owner.getMoney() + price);
        }
        Object[] param = {count,owner.getId()};
        int updateOwner = ownerDao.updateOwner(updateOwnerSql, param);
        if (updateOwner > 0) {
            System.out.println("成功修改一条记录");
        }
        return updateOwner;
    }

    @Override
    /**
     * 根据宠物商店id查询宠物商店
     */
    public PetStore getPetStore(long id) {
        String sql = "select * from petStore where id=" + id;
        PetStoreDao petStoreDao = new PetStoreDaoImpl();
        PetStore petStore = petStoreDao.getPetStore(sql, null);
        return petStore;
    }

    @Override
    /**
     * 修改宠物商店信息 type=0是主人买宠物,type=1是商店买宠物
     */
    public int modifyStore(Pet pet, int type) {
        PetStoreService store = new PetStoreServiceImpl();
        PetStore petStore = store.getPetStore(pet.getStoreId());
        String updateStoreSql = "update petStore set balance=? where id=?";
        double price = charge(pet);
        double count = 0;
        //主人买宠物
        if (type == 0) {
            count = petStore.getBalance() + price;
        }
        //商店买宠物
        if (type == 1) {
            count = petStore.getBalance() - price;
        }
        Object[] param = {count, petStore.getId()};
        PetStoreDao storeDao = new PetStoreDaoImpl();
        //执行sql语句,修改商店信息
        int updateStore = storeDao.updateStore(updateStoreSql, param);
        return updateStore;
    }

    @Override
    /**
     * 宠物商店登录
     */
    public PetStore login() {
        Scanner input = new Scanner(System.in);
        PetStore petStore = null;
        //1.输入宠物商店名字
        boolean type = true;
        while (type) {
            System.out.println("请先登录,请输入宠物商店名字:");
            String storeName = input.nextLine().trim();
            System.out.println("请输入宠物商店的密码");
            String storePassword = input.nextLine().trim();
            PetStoreDao petStoreDao = new PetStoreDaoImpl();
            String sql = "select * from petStore where name=? and password=?";
            String[] param = {storeName, storePassword};
            petStore = petStoreDao.getPetStore(sql, param);
            if (petStore != null) {
                System.out.println("-----恭喜登录成功-----");
                System.out.println("-----宠物商店的基本信息-----");
                System.out.println("名称:" + petStore.getName());
                System.out.println("元宝数:" + petStore.getBalance());
                type = false;
            } else {
                System.out.println("登录失败,请重新登录");
                type = true;
            }
        }
        return petStore;
    }

    @Override
    /**
     * 查询所有宠物主人正在出售的宠物
     */
    public List<Pet> getPetSelling() {
        PetDao petDao = new PetDaoImpl();
        String sql = "select * from pet where owner_id is not null";
        String[] param = null;
        List<Pet> petList = petDao.selectPet(sql, param);
        return petList;
    }

    @Override
    /**
     * 查询宠物商店台账信息
     */
    public List<Account> account(long storeId) {
        String sql = "select * from account where deal_type=? and seller_id=? union select * from account where deal_type=? and buyer_id=?";
        String[] param = {"1", String.valueOf(storeId), "2", String.valueOf(storeId)};
        AccountDao accountDao = new AccountDaoImpl();
        List<Account> list = accountDao.getPetStoreAccount(sql, param);
        return list;
    }

    @Override
    /**
     * 宠物商店卖宠物给主人
     */
    public int modifyAccount(Pet pet, PetOwner owner) {
        String insertSql = "insert into account(deal_type,pet_id,seller_id,buyer_id,price,deal_time) values(?,?,?,?,?,?)";
        double price = charge(pet);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Object[] insertParam = {1, pet.getId(), pet.getStoreId(), owner.getId(), price, date};
        AccountDao accountDao = new AccountDaoImpl();
        int insertAccount = accountDao.updateAccount(insertSql, insertParam);
        return insertAccount;
    }

    @Override
    /**
     * 宠物繁育(创建一个宠物)
     */
    public Pet bread(String petType) {
        Scanner input = new Scanner(System.in);
        System.out.println("请在下面输入宠物属性:");
        System.out.println("请输入宠物的名字");
        String petName = input.nextLine().trim();
        System.out.println("请输入宠物的健康指数(整型)");
        String petHealth = input.nextLine();
        System.out.println("请输入宠物爱心指数(整型)");
        String petLove = input.nextLine();
        System.out.println("请输入宠物所属宠物商店的Id(整数)");
        String storeId = input.nextLine();
        String sql = "insert into pet(name,typeName,health,love,birthday,store_id) values(?,?,?,?,?,?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String time = sdf.format(date);
        Object[] param = {petName, petType, petHealth, petLove, time, storeId};
        String[] petParams = {petName, petType, petHealth, petLove, storeId};
        PetFactory petFactory = new PetFactoryImpl();
        //使用宠物工厂的方法创建pet对象
        Pet pet = petFactory.breadNewPet(petParams);
        PetDao petDao = new PetDaoImpl();
        //count用于判断是否成功执行sql语句
        int count = petDao.updatePet(sql, param);
        if (count > 0) {
            System.out.println("成功创建了一只" + pet.getName());
        }
        return pet;
    }

    @Override
    /**
     * 商店购买宠物
     */
    public void buy(Pet pet) {
        //查询宠物所在的商店
        String sql = "select * from petStore where id=?";
        String[] param = {String.valueOf(pet.getStoreId())};
        PetStoreDao petStoreDao = new PetStoreDaoImpl();
        PetStore petStore = petStoreDao.getPetStore(sql, param);
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();
        //查找宠物的主人
        sql = "select * from petOwner where id=?";
        String[] ownerParam = {String.valueOf(pet.getOwnerId())};
        PetOwner petOwner = petOwnerDao.selectOwner(sql, ownerParam);
        //修改宠物信息
        int updatePet = modifyPet(pet, null, petStore);
        //更新宠物主人信息
        if (updatePet > 0) {
            int updateOwner = modifyOwner(petOwner, pet, 1);
            //更新宠物商店信息
            if (updateOwner > 0) {
                int updateStore = modifyStore(pet, 1);
                //更新台账信息
                if (updateStore > 0) {
                    int updateAccount = modifyAccount(pet, petOwner);
                    if (updateAccount > 0) {
                        System.out.println("台账正确插入一条信息");
                    }
                }
            }

        }
    }

    @Override
    public List<PetStore> getAllStore() {
        PetStoreDao petStoreDao = new PetStoreDaoImpl();
        List<PetStore> petStoreList = petStoreDao.getAllStore();
        return petStoreList;
    }

    @Override
    /**
     * 商店卖宠物
     */
    public void sell(Pet pet) {
        PetDao petDao = new PetDaoImpl();
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();
        PetStoreDao petStoreDao = new PetStoreDaoImpl();
        PetStoreService petStoreService = new PetStoreServiceImpl();
        String sql = "update pet set store_id=null,owner_id=? where id=?";
        Object[] param = {pet.getOwnerId(), pet.getId()};
        //修改宠物信息
        int updatePet = petDao.updatePet(sql, param);
        //修改宠物主人信息
        if (updatePet > 0) {
            String ownerSql = "select * from petOwner where id=?";
            String[] ownerParam = {String.valueOf(pet.getOwnerId())};
            PetOwner petOwner = petOwnerDao.selectOwner(ownerSql, ownerParam);
            String updateOwnerSql = "update petOwner set money=? where id=?";
            double count = petStoreService.charge(pet);
            double money = petOwner.getMoney() - count;
            Object[] updateOwnerParam = {money, petOwner.getId()};

            int updateOwnerpdate = petOwnerDao.updateOwner(updateOwnerSql, updateOwnerParam);
            if (updateOwnerpdate > 0) {
                PetStore petStore = petStoreService.getPetStore(pet.getStoreId());
                String updateStoreSql = "update petStore set balance=? where id=?";
                double balance = petStore.getBalance() + count;
                Object[] storeParam = {balance, petStore.getId()};
                int updateStore = petStoreDao.updateStore(updateStoreSql, storeParam);
                //更新台账信息
                if (updateStore > 0) {
                    AccountDao accountDao = new AccountDaoImpl();
                    String insertSql = "insert into account(deal_type,pet_id,seller_id,buyer_id,price,deal_time) values(?,?,?,?,?,?)";
                    //时间格式化
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String time = sdf.format(date);
                    Object[] accountParam = {1, pet.getId(), petStore.getId(), petOwner.getId(), count, time};
                    int insertAccount = accountDao.updateAccount(insertSql, accountParam);
                    if (insertAccount > 0) {
                        System.out.println("成功插入一条台账信息");
                    }

                }
            }
        }


    }
}
