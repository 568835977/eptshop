package com.learn.epetShop.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: eptshop
 * @description: 数据库操作基类
 * @author: Mr.Xie
 * @create: 2018-09-11 19:36
 **/
public class BaseDao {
    public static String DRIVER;
    public static String URL;
    public static String DBNAME;
    public static String DBPASS;
    Connection conn = null;
    static {
        init();
    }

    /**
     * 初始化连接参数,从配置文件里获得
     */
    public static void init(){
        Properties params = new Properties();
        //配置文件路径
        String configFile = "database.properties";
        //加载配置文件到输入流中
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        try {
            params.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //根据指定的获取对应的值
        DRIVER = params.getProperty("driver");
        URL = params.getProperty("url");
        DBNAME = params.getProperty("user");
        DBPASS = params.getProperty("password");
    }

    /**
     * 得到数据库连接
     * @return conn
     */
    public Connection getConn(){
        Connection conn = null;
        try {
            //加载驱动
            Class.forName(DRIVER);
            //获取数据库连接
            conn = DriverManager.getConnection(URL,DBNAME,DBPASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs){
        /* 如果rs不为空,则关闭rs */
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pstmt!=null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行SQL语句,可以进行增,删,改的操作,不能执行查询
     * @param preparedSql 预编译的SQL语句
     * @param param 预编译的SQL语句中的'?' 参数的字符串数组
     * @return int 影响的条数
     */
    public int executeSQL(String preparedSql, Object[] param){
        Connection conn = null;
        PreparedStatement pstmt = null;
        int num = 0;

        //获取数据库连接
        conn = getConn();
        try {
            //得到pstmt对象
            pstmt = conn.prepareStatement(preparedSql);
            if (param!=null){
                for (int i = 0; i < param.length;i++){
                    //为预编译设置参数
                    pstmt.setObject(i+1,param[i]);
                }
            }
            System.out.println(preparedSql);
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }
}
