package com.learn.epetShop.dao.impl;

import com.learn.epetShop.dao.BaseDao;
import com.learn.epetShop.dao.PetStoreDao;
import com.learn.epetShop.entity.PetStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: eptshop
 * @description: PetStoreDao实现类
 * @author: Mr.Xie
 * @create: 2018-09-12 13:06
 **/
public class PetStoreDaoImpl extends BaseDao implements PetStoreDao {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    @Override
    public List<PetStore> getAllStore() {
        List<PetStore> storeList = new ArrayList<>();
        conn = getConn();
        try {
            String sql = "select * from petStore";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PetStore petStore = new PetStore();
                petStore.setId(rs.getInt(1));
                petStore.setName(rs.getString(2));
                petStore.setPassword(rs.getString(3));
                petStore.setBalance(rs.getInt(4));
                storeList.add(petStore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return storeList;
    }

    @Override
    public PetStore getPetStore(String sql, String[] param) {
        PetStore petStore = new PetStore();
        conn = getConn();
        try {
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setString(i + 1, param[i]);
                }
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                petStore.setId(rs.getInt(1));
                petStore.setName(rs.getString(2));
                petStore.setPassword(rs.getString(3));
                petStore.setBalance(rs.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return petStore;
    }

    @Override
    public int updateStore(String sql, Object[] param) {
        int count = executeSQL(sql, param);
        return count;
    }
}
