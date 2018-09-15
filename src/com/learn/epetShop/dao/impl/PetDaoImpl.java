package com.learn.epetShop.dao.impl;

import com.learn.epetShop.dao.BaseDao;
import com.learn.epetShop.dao.PetDao;
import com.learn.epetShop.entity.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: eptshop
 * @description: PetDao实现类
 * @author: Mr.Xie
 * @create: 2018-09-11 21:56
 **/
public class PetDaoImpl extends BaseDao implements PetDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    @Override
    public List<Pet> getAllPet() {
        List<Pet> petList = new ArrayList<>();
        String preparedSql = "select * from pet";
        conn = getConn();

        try {
            pstmt = conn.prepareStatement(preparedSql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt(1));
                pet.setName(rs.getString(2));
                pet.setTypeName(rs.getString(3));
                pet.setHealth(rs.getInt(4));
                pet.setLove(rs.getInt(5));
                pet.setBirthday(rs.getDate(6));
                pet.setOwnerId(rs.getInt(7));
                pet.setStoreId(rs.getInt(8));
                petList.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return petList;
    }

    @Override
    public List<Pet> selectPet(String sql, String[] param) {
        List<Pet> petList = new ArrayList<>();
        conn = getConn();
        try {
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    //为预编译设置参数
                    pstmt.setString(i+1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt(1));
                pet.setName(rs.getString(2));
                pet.setTypeName(rs.getString(3));
                pet.setHealth(rs.getInt(4));
                pet.setLove(rs.getInt(5));
                pet.setBirthday(rs.getDate(6));
                pet.setOwnerId(rs.getInt(7));
                pet.setStoreId(rs.getInt(8));
                petList.add(pet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return petList;
    }

    @Override
    public int updatePet(String sql, Object[] param) {
        int count = super.executeSQL(sql,param);
        return count;
    }
}
