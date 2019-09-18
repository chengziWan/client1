package org.suresec.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
/**
 * 
 * @author wcc
 * @time 2019-09-18 11:09
 * @description 连接数据库公共类
 */
public class DataBaseUtil {
	private static final String URL="jdbc:mysql://127.0.0.1:3306/cc2?useUnicode=true&characterEncoding=UTF-8";  // 一般默认3306，这里设置成6666  （33060）  MYSQL8  WMPNetworkSvc
	private static final String  USER="root";
	private static final String  PASSWORD="123456";
	private static Connection connection=null;
 
	static{
		//1、加载驱动程序（反射的方法）
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//2、连接数据库
		try {
		connection=(Connection) DriverManager.
					getConnection(URL, USER,PASSWORD);//地址，用户名，密码

		System.out.println("连接成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		return connection;
	}

}
