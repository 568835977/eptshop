package com.learn.epetShop.dao.impl;

import com.learn.epetShop.dao.AccountDao;
import com.learn.epetShop.dao.BaseDao;
import com.learn.epetShop.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: eptshop
 * @description: AccountDao实现类
 * @author: Mr.Xie
 * @create: 2018-09-11 20:37
 **/
public class AccountDaoImpl extends BaseDao implements AccountDao {
    /**
     * 保存数据库连接
     */
    private Connection conn = null;
    /**
     * 执行SQL语句
     */
    private PreparedStatement pstmt = null;
    /**
     * 用户保存查询结果集
     */
    private ResultSet rs = null;

    @Override
    public int updateAccount(String sql, Object[] param) {
        int count = super.executeSQL(sql,param);
        return count;
    }


    @Override
    public List<Account> getPetStoreAccount(String sql, String[] param) {
        List<Account> accountList = new ArrayList<>();
        try {
            //得到数据库连接
            conn = getConn();
            //得到PreparedStatement对象
            pstmt = conn.prepareStatement(sql);
            if (param!=null){
                for (int i = 0; i < param.length;i++){
                    pstmt.setString(i+1,param[i]);
                }
            }
            //执行SQL语句
            rs = pstmt.executeQuery();
            Account account =  null;
            while (rs.next()){
                account = new Account();
                account.setId(rs.getInt(1));
                account.setDealType(rs.getInt(2));
                account.setPetId(rs.getInt(3));
                account.setSellerId(rs.getInt(4));
                account.setBuyerId(rs.getInt(5));
                account.setPrice(rs.getDouble(6));
                account.setDealTime(rs.getDate(7));
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            super.closeAll(conn,pstmt,rs);
        }
        return accountList;
    }
}
