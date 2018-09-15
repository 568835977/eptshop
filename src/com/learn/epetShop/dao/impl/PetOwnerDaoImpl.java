package com.learn.epetShop.dao.impl;

import com.learn.epetShop.dao.BaseDao;
import com.learn.epetShop.dao.PetOwnerDao;
import com.learn.epetShop.entity.PetOwner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program eptshop 电子宠物商店
 * @description PetOwnerDao实现类
 * @author  Mr.Xie
 * @create 2018-09-12 12:48
 **/
public class PetOwnerDaoImpl extends BaseDao implements PetOwnerDao {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    @Override
    public List<PetOwner> getAllOwner() {
        List<PetOwner> ownerList = new ArrayList<>();
        conn = getConn();
        try {
            pstmt = conn.prepareStatement("select * from petOwner");
            rs = pstmt.executeQuery();
            while (rs.next()){
                PetOwner petOwner = new PetOwner();
                petOwner.setId(rs.getInt(1));
                petOwner.setName(rs.getString(2));
                petOwner.setPassword(rs.getString(3));
                petOwner.setMoney(rs.getDouble(4));
                ownerList.add(petOwner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(conn,pstmt,rs);
        }
        return ownerList;
    }

    @Override
    public int updateOwner(String sql, Object[] param) {
        int count = executeSQL(sql,param);
        return count;
    }

    @Override
    public PetOwner selectOwner(String sql, String[] param) {
        PetOwner petOwner = null;
        conn = getConn();
        try {
            pstmt = conn.prepareStatement(sql);
            for (int i = 0;i < param.length; i++){
                pstmt.setString(i+1,param[i]);
            }
            rs = pstmt.executeQuery();
            while (rs.next()){
                petOwner = new PetOwner();
                petOwner.setId(rs.getInt(1));
                petOwner.setName(rs.getString(2));
                petOwner.setPassword(rs.getString(3));
                petOwner.setMoney(rs.getDouble(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(conn,pstmt,rs);
        }
        return petOwner;
    }
}
