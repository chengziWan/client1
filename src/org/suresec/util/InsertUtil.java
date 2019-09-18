package org.suresec.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
/**
 * 
 * @author wcc
 * @time 2019-09-18 11:10
 * @description 批量插入--智能审计系统
 */
public class InsertUtil {
	private int perNum = 1000;//每页多少条

	private static Connection connection=DataBaseUtil.getConnection();
	//resultSet转换为list
	public static List<Map<String, Object>> ResultSetToList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		List<String> colNameList = new ArrayList<String>();
		for (int i = 0; i < colCount; i++) {
			colNameList.add(rsmd.getColumnLabel(i + 1));
		}
		while (rs.next()) {
			Map map = new HashMap<String, Object>();
			for (int i = 0; i < colCount; i++) {
				
				String key = colNameList.get(i);
				Object value = rs.getString(colNameList.get(i));
				map.put(key, value);
				
			}
			results.add(map);
		}
		return results;
	}
	////////////////////
	// 删除所有数据
	public void deleteAllDatas() {
		System.out.println("开始删除");

		String[] sql = new String[] { "delete from tb_sus_report;", "delete from tb_lar_report;",
				"delete from tb_lwhc_log;", "delete from tb_risk_his;", "delete from tb_risk_new;",
				"delete from tb_cash_convert;", "delete from tb_cash_remit;", "delete from tb_cred_txn;",
				"delete from tb_cross_border;", "delete from tb_acc_txn;", "delete from tb_acc;",
				"delete from tb_cst_pers;", "delete from tb_settle_type;", "delete from temp_settle_type;",
				"delete from tb_cst_unit;", "delete FROM tb_bank;", "delete FROM tb_hbank;",
				"delete FROM tb_hbank_table;" };

		try {
			connection.setAutoCommit(false);// 关闭事务
			Statement statement = connection.createStatement();
			for (int i = 0; i < sql.length; i++) {
				statement.addBatch(sql[i]);
			}
			statement.executeBatch();
			connection.commit();
			System.out.println("删除成功");
		} catch (SQLException e) {
			System.out.println("删除失败");
			e.printStackTrace();
		}
	}
	// 删除表
	public void dropTables() {
		System.out.println("开始删除表");
		
		List<Map<String,Object>> list = selectTableList("tb_hbank_table");
		String[] sql = new String[list.size()];
		try {
			connection.setAutoCommit(false);// 关闭事务
			Statement statement = connection.createStatement();
			int i=0;
			for(Map<String,Object> mp:list) {
				sql[i] = "drop table "+mp.get("table_name")+";";
				statement.addBatch(sql[i]);
				i++;
			}
			statement.executeBatch();
			connection.commit();
			System.out.println("删除表成功");
		} catch (SQLException e) {
			System.out.println("删除表失败");
			e.printStackTrace();
		}
	}
	
	//批量插入--0tb_hbank报告机构表
	public void batchAddTb_hbank(){
		System.out.println("开始插入0tb_hbank报告机构表");
		
		String sql="INSERT INTO `tb_hbank` VALUES ('BGJG102','中国工商银行'),('BGJG103','中国农业银行'),('BGJG104','中国银行'),('BGJG105','中国建设银行'),('BGJG201','国家开发银行'),('BGJG202','中国进出口银行');";
		
		try {
			connection.setAutoCommit(false);// 关闭事务
			Statement statement = connection.createStatement();
			statement.execute(sql);
			connection.commit();
			System.out.println("插入成功0tb_hbank报告机构表");
		} catch (SQLException e) {
			System.out.println("插入失败0tb_hbank报告机构表");
			e.printStackTrace();
		}
	}
	//批量插入--1tb_bank机构对照表
	public void batchAddTb_bank(String suffix){
		System.out.println("开始插入1tb_bank机构对照表");
		
		String sql="insert into tb_bank"+suffix+" values('BGJG102','JGWD102','JRJG102','中国工商银行','10'), " + 
				"('BGJG102001','JGWD102001','JRJG102001','中国工商银行山东省分行','10'), " + 
				"('BGJG102','JGWD102001001','JRJG102001001','中国工商银行青岛市分行','10'), " + 
				"('BGJG102','JGWD102001002','JRJG102001002','中国工商银行济南市分行','10'), " + 
				"('BGJG102','JGWD102001003','JRJG102001003','中国工商银行淄博市分行','10'), " + 
				"('BGJG102','JGWD102001004','JRJG102001004','中国工商银行烟台市分行','10'), " + 
				"('BGJG102','JGWD102001005','JRJG102001005','中国工商银行泰安市分行','10'), " + 
				"('BGJG102','JGWD102001006','JRJG102001006','中国工商银行滨州市分行','10'), " + 
				"('BGJG102','JGWD102001007','JRJG102001007','中国工商银行德州市分行','10'), " + 
				"('BGJG102','JGWD102001008','JRJG102001008','中国工商银行济宁市分行','10'), " + 
				"('BGJG102','JGWD102001009','JRJG102001009','中国工商银行日照市分行','10'), " + 
				"('BGJG102','JGWD102001010','JRJG102001010','中国工商银行威海市分行','10'), " + 
				"('BGJG102','JGWD102001011','JRJG102001011','中国工商银行潍坊市分行','10'), " + 
				"('BGJG103','JGWD103','JRJG103','中国农业银行','10'), " + 
				"('BGJG103','JGWD103002','JRJG103002','中国农业银行山东省分行','10'), " + 
				"('BGJG103','JGWD103002001','JRJG103002001','中国农业银行青岛市分行','10'), " + 
				"('BGJG103','JGWD103002002','JRJG103002002','中国农业银行济南市分行','10'), " + 
				"('BGJG103','JGWD103002003','JRJG103002003','中国农业银行淄博市分行','10'), " + 
				"('BGJG103','JGWD103002004','JRJG103002004','中国农业银行烟台市分行','10'), " + 
				"('BGJG103','JGWD103002005','JRJG103002005','中国农业银行泰安市分行','10'), " + 
				"('BGJG103','JGWD103002006','JRJG103002006','中国农业银行滨州市分行','10'), " + 
				"('BGJG103','JGWD103002007','JRJG103002007','中国农业银行德州市分行','10'), " + 
				"('BGJG103','JGWD103002008','JRJG103002008','中国农业银行济宁市分行','10'), " + 
				"('BGJG103','JGWD103002009','JRJG103002009','中国农业银行日照市分行','10'), " + 
				"('BGJG103','JGWD103002010','JRJG103002010','中国农业银行威海市分行','10'), " + 
				"('BGJG103','JGWD103002011','JRJG103002011','中国农业银行潍坊市分行','10'), " + 
				"('BGJG104','JGWD104','JRJG104','中国银行','10'), " + 
				"('BGJG104','JGWD104003','JRJG104003','中国银行山东省分行','10'), " + 
				"('BGJG104','JGWD104003001','JRJG104003001','中国银行青岛市分行','10'), " + 
				"('BGJG104','JGWD104003002','JRJG104003002','中国银行济南市分行','10'), " + 
				"('BGJG104','JGWD104003003','JRJG104003003','中国银行淄博市分行','10'), " + 
				"('BGJG104','JGWD104003004','JRJG104003004','中国银行烟台市分行','10'), " + 
				"('BGJG104','JGWD104003005','JRJG104003005','中国银行泰安市分行','10'), " + 
				"('BGJG104','JGWD104003006','JRJG104003006','中国银行滨州市分行','10'), " + 
				"('BGJG104','JGWD104003007','JRJG104003007','中国银行德州市分行','10'), " + 
				"('BGJG104','JGWD104003008','JRJG104003008','中国银行济宁市分行','10'), " + 
				"('BGJG104','JGWD104003009','JRJG104003009','中国银行日照市分行','10'), " + 
				"('BGJG104','JGWD104003010','JRJG104003010','中国银行威海市分行','10'), " + 
				"('BGJG104','JGWD104003011','JRJG104003011','中国银行潍坊市分行','10'), " + 
				"('BGJG105','JGWD105','JRJG105','中国建设银行','10'), " + 
				"('BGJG201','JGWD201','JRJG201','国家开发银行','10'), " + 
				"('BGJG202','JGWD202','JRJG202','中国进出口银行','11');";
		
		try {
			connection.setAutoCommit(false);// 关闭事务
			Statement statement = connection.createStatement();
			statement.execute(sql);
			connection.commit();
			System.out.println("插入成功1tb_bank机构对照表");
		} catch (SQLException e) {
			System.out.println("插入失败1tb_bank机构对照表");
			e.printStackTrace();
		}
	}
	//批量插入--2tb_settle_type业务类型对照表
	public void batchAddTb_settle_type(String suffix){
		System.out.println("开始插入2tb_settle_type业务类型对照表");
		
		String sql="INSERT INTO `tb_settle_type"+suffix+"` VALUES ('"+suffix+"','1001','取现'),('"+suffix+"','1002','存款'),('"+suffix+"','1003','转入'),('"+suffix+"','1004','转出'),('"+suffix+"','1005','现金汇款'),('"+suffix+"','1006','现钞兑换'),('"+suffix+"','1007','信用卡交易');";
		try {
			connection.setAutoCommit(false);// 关闭事务
			Statement statement = connection.createStatement();
			statement.execute(sql);
			connection.commit();
			System.out.println("插入成功2tb_settle_type业务类型对照表");
		} catch (SQLException e) {
			System.out.println("插入失败2tb_settle_type业务类型对照表");
			e.printStackTrace();
		}
//		String sql2="insert into tb_settle_type"+suffix+" (" + 
//				"select head_no,settle_type,`name` from (select distinct head_no from tb_bank) tb_bank,temp_settle_type" + 
//				")";
//		try {
//			connection.setAutoCommit(false);// 关闭事务
//			Statement statement = connection.createStatement();
//			statement.execute(sql2);
//			connection.commit();
//			System.out.println("插入成功2tb_settle_type业务类型对照表");
//		} catch (SQLException e) {
//			System.out.println("插入失败2tb_settle_type业务类型对照表");
//			e.printStackTrace();
//		}
	}
	
	//批量插入--3tb_cst_pers存量个人客户身份信息表
	public void batchAddTb_cst_pers(List<Map<String,String>> list,String suffix){
		System.out.println("开始插入3tb_cst_pers存量个人客户身份信息表");
		
		String sql="INSERT INTO `tb_cst_pers"+suffix+"` (`head_no`, `bank_code1`, `cst_no`, `open_time`, `close_time`, `acc_name`, `cst_sex`, `nation`, `id_type`, `id_no`, `id_deadline`, `occupation`, `income`, `contact1`, `contact2`, `contact3`, `address1`, `address2`, `address3`, `company`, `sys_name`) VALUES "
				+ "('BGJG102', 'JGWD102', ?, '20190903', '20191003', ?, ?, 'CHN', '11', ?, '20220302', '医生', '200002.00', ?, '@N', '@N', ?, '@N', '@N', '京东', '工商融易联');";
		java.sql.PreparedStatement ptmt = null;
		
		try {
			connection.setAutoCommit(false);// 关闭事务
			ptmt = connection.prepareStatement(sql);
		
		} catch (SQLException e2) {
			e2.printStackTrace();
		} 
		
		for (Map<String,String> mp : list) {
			
			try {
				ptmt.setString(1, mp.get("cst_no"));
				ptmt.setString(2, mp.get("acc_name"));
				ptmt.setString(3, mp.get("cst_sex"));
				ptmt.setString(4, mp.get("id_no"));
				ptmt.setString(5, mp.get("contact1"));
				ptmt.setString(6, mp.get("address1"));
				ptmt.addBatch();	
				}	
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			ptmt.executeBatch();//执行给定的SQL语句，该语句可能返回多个结果
			connection.commit();
			System.out.println("插入成功3tb_cst_pers存量个人客户身份信息表");
		} catch (SQLException e) {
			System.out.println("插入失败3tb_cst_pers存量个人客户身份信息表");
			e.printStackTrace();
		}
	}
	//批量插入--4tb_cst_unit存量单位客户身份信息表
	public void batchAddTb_cst_unit(List<Map<String,String>> list,String suffix){
		System.out.println("开始插入4tb_cst_unit存量单位客户身份信息表");
		
		String sql="INSERT INTO `tb_cst_unit"+suffix+"` (`head_no`,`bank_code1`,`cst_no`,`open_time`,`acc_name`,`address`,`operate`,`set_file`,`license`,`id_deadline`,`org_no`,`tax_no`,`rep_name`,`id_type2`,`id_no2`,`id_deadline2`,`man_name`,`id_type3`,`id_no3`,`id_deadline3`,`ope_name`,`id_type4`,`id_no4`,`id_deadline4`,`industry`,`reg_amt`,`code`,`sys_name`) VALUES "
				+ "('BGJG102', 'JGWD102', ?, '20190903', ?, ?,'主营xxx业务', '11', ?, '20221021','77301597-6', '91210211773015976N',  ?, '11',?, '20220304',?, '11',?, '20220304', '李易联', '11', '380687198006024939', '20291021', '制造业',100000,'RMB','系统名称');";
		java.sql.PreparedStatement ptmt = null;
		
		try {
			connection.setAutoCommit(false);// 关闭事务
			ptmt = connection.prepareStatement(sql);
		
		} catch (SQLException e2) {
			e2.printStackTrace();
		} 
		int i = 100;
		for (Map<String,String> mp : list) {
			
			try {
				i++;
				ptmt.setString(1, mp.get("cst_no")+i);
				ptmt.setString(2, mp.get("acc_name")+"有限公司");
				ptmt.setString(3, mp.get("address1"));
				ptmt.setString(4, "3500440012"+i);
				ptmt.setString(5, mp.get("acc_name"));
				ptmt.setString(6, mp.get("id_no"));
				ptmt.setString(7, mp.get("acc_name"));
				ptmt.setString(8, mp.get("id_no"));
				ptmt.addBatch();	
				}	
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			ptmt.executeBatch();//执行给定的SQL语句，该语句可能返回多个结果
			connection.commit();
			System.out.println("插入成功4tb_cst_unit存量单位客户身份信息表");
		} catch (SQLException e) {
			System.out.println("插入失败4tb_cst_unit存量单位客户身份信息表");
			e.printStackTrace();
		}
	}
	
	// 批量插入--5tb_acc符合特定条件的银行账户信息表
	public void batchAddTb_acc(String sql_data,String sql_recodeCount,String suffix) {
		System.out.println("开始插入5tb_acc符合特定条件的银行账户信息表");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		try {
			
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ptmt = null;
		String sql = "INSERT INTO `tb_acc"+suffix+"` (`head_no`, `bank_code1`, `self_acc_name`, `acc_state`, `self_acc_no`, `card_no`, `acc_type`, `acc_type1`, `id_no`, `cst_no`, `fixed_flag`, `ent_cst_type`, `frg_flag`, `open_time`, `close_time`, `acc_flag`, `credit_flag`, `w_type`, `bank_tel`, `open_type`, `open_type1`, `agent_name`, `agent_tel`, `agent_type`, `agent_no`) VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		sql_data += " limit ?,"+perNum;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					try {
						ptmt.setString(1, mp.getString("head_no") + "");
						ptmt.setString(2, mp.getString("bank_code1") + "");
						ptmt.setString(3, mp.getString("self_acc_name") + "");
						ptmt.setString(4, mp.getString("acc_state").toString());
						// ptmt.setString(5,mp.getString("self_acc_no")+"");
						// ptmt.setString(6,mp.getString("card_no")+"");
						String card_no = RandomUtils.getCardNo();
						ptmt.setString(5, card_no);
						ptmt.setString(6, card_no);
						ptmt.setString(7, mp.getString("acc_type") + "");
						ptmt.setString(8, mp.getString("acc_type1") + "");
						ptmt.setString(9, mp.getString("id_no") + "");
						ptmt.setString(10, mp.getString("cst_no") + "");
						ptmt.setString(11, mp.getString("fixed_flag") + "");
						ptmt.setString(12, mp.getString("ent_cst_type") + "");
						ptmt.setString(13, mp.getString("frg_flag") + "");
						ptmt.setString(14, mp.getString("open_time") + "");
						ptmt.setString(15, mp.getString("close_time") + "");
						ptmt.setString(16, mp.getString("acc_flag") + "");
						ptmt.setString(17, mp.getString("credit_flag") + "");
						ptmt.setString(18, mp.getString("w_type") + "");
						ptmt.setString(19, mp.getString("bank_tel") + "");
						ptmt.setString(20, mp.getString("open_type") + "");
						ptmt.setString(21, mp.getString("open_type1") + "");
						ptmt.setString(22, mp.getString("agent_name") + "");
						ptmt.setString(23, mp.getString("agent_tel") + "");
						ptmt.setString(24, mp.getString("agent_type") + "");
						ptmt.setString(25, mp.getString("agent_no") + "");

						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功5tb_acc符合特定条件的银行账户信息表");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败5tb_acc符合特定条件的银行账户信息表");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}
	//批量插入--6tb_acc_txn基于客户账户的交易数据表
	public void batchAddTb_acc_txn(int total,String suffix){
		System.out.println("开始插入6tb_acc_txn基于客户账户的交易数据表");
		//要执行的SQL语句
		String sql_data = "SELECT '20190909' `date`," + 
				"'141133' `time`," + 
				"'JGWD102' `self_bank_code`," + 
				"acc_type `acc_type`," + 
				"cst_no `cst_no`," + 
				"id_no `id_no`," + 
				"self_acc_no `self_acc_no`," + 
				"card_no `card_no`," + 
				"self_acc_name `self_acc_name`," + 
				"'11' `acc_flag`," + 
				"'@N' `part_bank_code`/**/," + 
				"'@N' `part_bank_name`/**/," + 
				"'@N' `part_acc_no`/**/," + 
				"'@N' `part_acc_name`/**/," + 
				"'11' `lend_flag`/*11-付；10-收*/," + 
				"'10' `tsf_flag`/*10：现金；11：转账*/," + 
				"'CNY' `cur`," + 
				"'720000' `org_amt`/**/," + 
				"'100000' `usd_amt`/**/," + 
				"'720000' `rmb_amt`/**/," + 
				"'720000' `balance`/**/ ," + 
				"'12' `agency_flag`," + 
				"'@N' `agent_name`," + 
				"'@N' `agent_tel`," + 
				"'@N' `agent_type`," + 
				"'@N' `agent_no`," + 
				"'YW201909091000001' `ticd`/**/ ," + 
				"'GYH0001' `counter_no`," + 
				"'1002' `settle_type`/**/ ," + 
				"'10' `reverse_flag`," + 
				"'摘要' `purpose`," + 
				"'12' `bord_flag`," + 
				"'@N' `nation`," + 
				"'14' `bank_flag`," + 
				"'127.0.0.1' `ip_code`," + 
				"'ATM00001' `atm_code`," + 
				"'JGWD102' `bank_code`," + 
				"'@N' `mac_info`" + 
				" from tb_acc"+suffix+" where  acc_type1 !='14' limit ?,"+perNum;
		//要执行的SQL语句
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type1 !='14'";
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ptmt = null;
		String sql="INSERT INTO `tb_acc_txn"+suffix+"` (`date`, `time`, `self_bank_code`, `acc_type`, `cst_no`, `id_no`, `self_acc_no`, `card_no`, `self_acc_name`, `acc_flag`, `part_bank_code`, `part_bank_name`, `part_acc_no`, `part_acc_name`, `lend_flag`, `tsf_flag`, `cur`, `org_amt`, `usd_amt`, `rmb_amt`, `balance`, `agency_flag`, `agent_name`, `agent_tel`, `agent_type`, `agent_no`, `ticd`, `counter_no`, `settle_type`, `reverse_flag`, `purpose`, `bord_flag`, `nation`, `bank_flag`, `ip_code`, `atm_code`, `bank_code`, `mac_info`) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ywSum = 10000001;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					BigDecimal balance=new BigDecimal(0.0);//余额
					try {
						//付+现金：存款
						balance = balance.add(new BigDecimal(10000));
						ptmt.setString(1, "" + mp.getString("date"));
						ptmt.setString(2, "" + mp.getString("time"));
						ptmt.setString(3, "" + mp.getString("self_bank_code"));
						ptmt.setString(4, "" + mp.getString("acc_type"));
						ptmt.setString(5, "" + mp.getString("cst_no"));
						ptmt.setString(6, "" + mp.getString("id_no"));
						ptmt.setString(7, "" + mp.getString("self_acc_no"));
						ptmt.setString(8, "" + mp.getString("card_no"));
						ptmt.setString(9, "" + mp.getString("self_acc_name"));
						ptmt.setString(10, "" + mp.getString("acc_flag"));
						ptmt.setString(11, "" + mp.getString("part_bank_code"));// 交易对方行代码;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(12, "" + mp.getString("part_bank_name"));// 交易对方行名称;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(13, "" + mp.getString("part_acc_no"));// 交易对方账号（卡号）;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(14, "" + mp.getString("part_acc_name"));// 交易对方户名;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(15, "" + mp.getString("lend_flag"));/* 11-付；10-收 */
						ptmt.setString(15, "" + "11");/* 11-付；10-收 */
//						ptmt.setString(16, "" + mp.getString("tsf_flag"));/* 10：现金；11：转账 */
						ptmt.setString(16, "" + "10");/* 10：现金；11：转账 */
						ptmt.setString(17, "" + mp.getString("cur"));
//						ptmt.setString(18, "" + mp.getString("org_amt"));// 原币种交易金额
//						ptmt.setString(19, "" + mp.getString("usd_amt"));// 折美元交易金额
//						ptmt.setString(20, "" + mp.getString("rmb_amt"));// 折人民币交易金额
//						ptmt.setString(21, "" + mp.getString("balance"));// 账户余额
						ptmt.setBigDecimal(18, new BigDecimal(10000));// 原币种交易金额
						ptmt.setBigDecimal(19, new BigDecimal(10000/7.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 折美元交易金额
						ptmt.setBigDecimal(20, new BigDecimal(10000));// 折人民币交易金额
						ptmt.setBigDecimal(21, balance);// 账户余额
						ptmt.setString(22, "" + mp.getString("agency_flag"));
						ptmt.setString(23, "" + mp.getString("agent_name"));
						ptmt.setString(24, "" + mp.getString("agent_tel"));
						ptmt.setString(25, "" + mp.getString("agent_type"));
						ptmt.setString(26, "" + mp.getString("agent_no"));
						// ptmt.setString(27,""+mp.getString("ticd"));//业务流水号YW201909091000001
						ptmt.setString(27, "" + "YW20190909" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(28, "" + mp.getString("counter_no"));
//						ptmt.setString(29, "" + mp.getString("settle_type"));// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(29, "" +"1002");// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(30, "" + mp.getString("reverse_flag"));
						ptmt.setString(31, "" + mp.getString("purpose"));
						ptmt.setString(32, "" + mp.getString("bord_flag"));
						ptmt.setString(33, "" + mp.getString("nation"));
						ptmt.setString(34, "" + mp.getString("bank_flag"));
						ptmt.setString(35, "" + mp.getString("ip_code"));
						ptmt.setString(36, "" + mp.getString("atm_code"));
						ptmt.setString(37, "" + mp.getString("bank_code"));
						ptmt.setString(38, "" + mp.getString("mac_info"));
						ywSum++;
						ptmt.addBatch();
						//付+现金：存款
						balance = balance.add(new BigDecimal(10000));
						ptmt.setString(1, "" + mp.getString("date"));
						ptmt.setString(2, "" + mp.getString("time"));
						ptmt.setString(3, "" + mp.getString("self_bank_code"));
						ptmt.setString(4, "" + mp.getString("acc_type"));
						ptmt.setString(5, "" + mp.getString("cst_no"));
						ptmt.setString(6, "" + mp.getString("id_no"));
						ptmt.setString(7, "" + mp.getString("self_acc_no"));
						ptmt.setString(8, "" + mp.getString("card_no"));
						ptmt.setString(9, "" + mp.getString("self_acc_name"));
						ptmt.setString(10, "" + mp.getString("acc_flag"));
						ptmt.setString(11, "" + mp.getString("part_bank_code"));// 交易对方行代码;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(12, "" + mp.getString("part_bank_name"));// 交易对方行名称;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(13, "" + mp.getString("part_acc_no"));// 交易对方账号（卡号）;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(14, "" + mp.getString("part_acc_name"));// 交易对方户名;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(15, "" + mp.getString("lend_flag"));/* 11-付；10-收 */
						ptmt.setString(15, "" + "11");/* 11-付；10-收 */
//						ptmt.setString(16, "" + mp.getString("tsf_flag"));/* 10：现金；11：转账 */
						ptmt.setString(16, "" + "10");/* 10：现金；11：转账 */
						ptmt.setString(17, "" + mp.getString("cur"));
//						ptmt.setString(18, "" + mp.getString("org_amt"));// 原币种交易金额
//						ptmt.setString(19, "" + mp.getString("usd_amt"));// 折美元交易金额
//						ptmt.setString(20, "" + mp.getString("rmb_amt"));// 折人民币交易金额
//						ptmt.setString(21, "" + mp.getString("balance"));// 账户余额
						ptmt.setBigDecimal(18, new BigDecimal(10000));// 原币种交易金额
						ptmt.setBigDecimal(19, new BigDecimal(10000/7.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 折美元交易金额
						ptmt.setBigDecimal(20, new BigDecimal(10000));// 折人民币交易金额
						
						ptmt.setBigDecimal(21, balance);// 账户余额
						ptmt.setString(22, "" + mp.getString("agency_flag"));
						ptmt.setString(23, "" + mp.getString("agent_name"));
						ptmt.setString(24, "" + mp.getString("agent_tel"));
						ptmt.setString(25, "" + mp.getString("agent_type"));
						ptmt.setString(26, "" + mp.getString("agent_no"));
						// ptmt.setString(27,""+mp.getString("ticd"));//业务流水号YW201909091000001
						ptmt.setString(27, "" + "YW20190909" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(28, "" + mp.getString("counter_no"));
//						ptmt.setString(29, "" + mp.getString("settle_type"));// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(29, "" +"1002");// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(30, "" + mp.getString("reverse_flag"));
						ptmt.setString(31, "" + mp.getString("purpose"));
						ptmt.setString(32, "" + mp.getString("bord_flag"));
						ptmt.setString(33, "" + mp.getString("nation"));
						ptmt.setString(34, "" + mp.getString("bank_flag"));
						ptmt.setString(35, "" + mp.getString("ip_code"));
						ptmt.setString(36, "" + mp.getString("atm_code"));
						ptmt.setString(37, "" + mp.getString("bank_code"));
						ptmt.setString(38, "" + mp.getString("mac_info"));
						ywSum++;
						ptmt.addBatch();
						//收+现金：取款
						balance = balance.subtract(new BigDecimal(10000));
						ptmt.setString(1, "" + mp.getString("date"));
						ptmt.setString(2, "" + mp.getString("time"));
						ptmt.setString(3, "" + mp.getString("self_bank_code"));
						ptmt.setString(4, "" + mp.getString("acc_type"));
						ptmt.setString(5, "" + mp.getString("cst_no"));
						ptmt.setString(6, "" + mp.getString("id_no"));
						ptmt.setString(7, "" + mp.getString("self_acc_no"));
						ptmt.setString(8, "" + mp.getString("card_no"));
						ptmt.setString(9, "" + mp.getString("self_acc_name"));
						ptmt.setString(10, "" + mp.getString("acc_flag"));
						ptmt.setString(11, "" + mp.getString("part_bank_code"));// 交易对方行代码;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(12, "" + mp.getString("part_bank_name"));// 交易对方行名称;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(13, "" + mp.getString("part_acc_no"));// 交易对方账号（卡号）;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(14, "" + mp.getString("part_acc_name"));// 交易对方户名;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(15, "" + mp.getString("lend_flag"));/* 11-付；10-收 */
						ptmt.setString(15, "" + "10");/* 11-付；10-收 */
//						ptmt.setString(16, "" + mp.getString("tsf_flag"));/* 10：现金；11：转账 */
						ptmt.setString(16, "" + "10");/* 10：现金；11：转账 */
						ptmt.setString(17, "" + mp.getString("cur"));
//						ptmt.setString(18, "" + mp.getString("org_amt"));// 原币种交易金额
//						ptmt.setString(19, "" + mp.getString("usd_amt"));// 折美元交易金额
//						ptmt.setString(20, "" + mp.getString("rmb_amt"));// 折人民币交易金额
//						ptmt.setString(21, "" + mp.getString("balance"));// 账户余额
						ptmt.setBigDecimal(18, new BigDecimal(10000));// 原币种交易金额
						ptmt.setBigDecimal(19, new BigDecimal(10000/7.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 折美元交易金额
						ptmt.setBigDecimal(20, new BigDecimal(10000));// 折人民币交易金额
						ptmt.setBigDecimal(21, balance);// 账户余额
						ptmt.setString(22, "" + mp.getString("agency_flag"));
						ptmt.setString(23, "" + mp.getString("agent_name"));
						ptmt.setString(24, "" + mp.getString("agent_tel"));
						ptmt.setString(25, "" + mp.getString("agent_type"));
						ptmt.setString(26, "" + mp.getString("agent_no"));
						// ptmt.setString(27,""+mp.getString("ticd"));//业务流水号YW201909091000001
						ptmt.setString(27, "" + "YW20190909" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(28, "" + mp.getString("counter_no"));
//						ptmt.setString(29, "" + mp.getString("settle_type"));// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(29, "" +"1001");// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(30, "" + mp.getString("reverse_flag"));
						ptmt.setString(31, "" + mp.getString("purpose"));
						ptmt.setString(32, "" + mp.getString("bord_flag"));
						ptmt.setString(33, "" + mp.getString("nation"));
						ptmt.setString(34, "" + mp.getString("bank_flag"));
						ptmt.setString(35, "" + mp.getString("ip_code"));
						ptmt.setString(36, "" + mp.getString("atm_code"));
						ptmt.setString(37, "" + mp.getString("bank_code"));
						ptmt.setString(38, "" + mp.getString("mac_info"));
						ywSum++;
						ptmt.addBatch();
						//收+转账：入账
						balance = balance.add(new BigDecimal(10000));
						ptmt.setString(1, "" + mp.getString("date"));
						ptmt.setString(2, "" + mp.getString("time"));
						ptmt.setString(3, "" + mp.getString("self_bank_code"));
						ptmt.setString(4, "" + mp.getString("acc_type"));
						ptmt.setString(5, "" + mp.getString("cst_no"));
						ptmt.setString(6, "" + mp.getString("id_no"));
						ptmt.setString(7, "" + mp.getString("self_acc_no"));
						ptmt.setString(8, "" + mp.getString("card_no"));
						ptmt.setString(9, "" + mp.getString("self_acc_name"));
						ptmt.setString(10, "" + mp.getString("acc_flag"));
//						ptmt.setString(11, "" + mp.getString("part_bank_code"));// 交易对方行代码;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(12, "" + mp.getString("part_bank_name"));// 交易对方行名称;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(13, "" + mp.getString("part_acc_no"));// 交易对方账号（卡号）;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(14, "" + mp.getString("part_acc_name"));// 交易对方户名;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(11, "" + "JGWD102");// 交易对方行代码;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(12, "" + "中国工商银行");// 交易对方行名称;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(13, "" + "6223097120684107998");// 交易对方账号（卡号）;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(14, "" + "冰原制冷成套设备有限公司");// 交易对方户名;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(15, "" + mp.getString("lend_flag"));/* 11-付；10-收 */
						ptmt.setString(15, "" + "10");/* 11-付；10-收 */
//						ptmt.setString(16, "" + mp.getString("tsf_flag"));/* 10：现金；11：转账 */
						ptmt.setString(16, "" + "11");/* 10：现金；11：转账 */
						ptmt.setString(17, "" + mp.getString("cur"));
//						ptmt.setString(18, "" + mp.getString("org_amt"));// 原币种交易金额
//						ptmt.setString(19, "" + mp.getString("usd_amt"));// 折美元交易金额
//						ptmt.setString(20, "" + mp.getString("rmb_amt"));// 折人民币交易金额
//						ptmt.setString(21, "" + mp.getString("balance"));// 账户余额
						ptmt.setBigDecimal(18, new BigDecimal(10000));// 原币种交易金额
						ptmt.setBigDecimal(19, new BigDecimal(10000/7.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 折美元交易金额
						ptmt.setBigDecimal(20, new BigDecimal(10000));// 折人民币交易金额
						ptmt.setBigDecimal(21, balance);// 账户余额
						ptmt.setString(22, "" + mp.getString("agency_flag"));
						ptmt.setString(23, "" + mp.getString("agent_name"));
						ptmt.setString(24, "" + mp.getString("agent_tel"));
						ptmt.setString(25, "" + mp.getString("agent_type"));
						ptmt.setString(26, "" + mp.getString("agent_no"));
						// ptmt.setString(27,""+mp.getString("ticd"));//业务流水号YW201909091000001
						ptmt.setString(27, "" + "YW20190909" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(28, "" + mp.getString("counter_no"));
//						ptmt.setString(29, "" + mp.getString("settle_type"));// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(29, "" +"1003");// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(30, "" + mp.getString("reverse_flag"));
						ptmt.setString(31, "" + mp.getString("purpose"));
						ptmt.setString(32, "" + mp.getString("bord_flag"));
						ptmt.setString(33, "" + mp.getString("nation"));
						ptmt.setString(34, "" + mp.getString("bank_flag"));
						ptmt.setString(35, "" + mp.getString("ip_code"));
						ptmt.setString(36, "" + mp.getString("atm_code"));
						ptmt.setString(37, "" + mp.getString("bank_code"));
						ptmt.setString(38, "" + mp.getString("mac_info"));
						ywSum++;
						//付+转账：出账
						balance = balance.subtract(new BigDecimal(10000));
						ptmt.setString(1, "" + mp.getString("date"));
						ptmt.setString(2, "" + mp.getString("time"));
						ptmt.setString(3, "" + mp.getString("self_bank_code"));
						ptmt.setString(4, "" + mp.getString("acc_type"));
						ptmt.setString(5, "" + mp.getString("cst_no"));
						ptmt.setString(6, "" + mp.getString("id_no"));
						ptmt.setString(7, "" + mp.getString("self_acc_no"));
						ptmt.setString(8, "" + mp.getString("card_no"));
						ptmt.setString(9, "" + mp.getString("self_acc_name"));
						ptmt.setString(10, "" + mp.getString("acc_flag"));
//						ptmt.setString(11, "" + mp.getString("part_bank_code"));// 交易对方行代码;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(12, "" + mp.getString("part_bank_name"));// 交易对方行名称;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(13, "" + mp.getString("part_acc_no"));// 交易对方账号（卡号）;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(14, "" + mp.getString("part_acc_name"));// 交易对方户名;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(11, "" + "JGWD102");// 交易对方行代码;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(12, "" + "中国工商银行");// 交易对方行名称;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(13, "" + "6223097120684107998");// 交易对方账号（卡号）;当Tsf_flag为10，即存取现金时，对方为空值
						ptmt.setString(14, "" + "冰原制冷成套设备有限公司");// 交易对方户名;当Tsf_flag为10，即存取现金时，对方为空值
//						ptmt.setString(15, "" + mp.getString("lend_flag"));/* 11-付；10-收 */
						ptmt.setString(15, "" + "11");/* 11-付；10-收 */
//						ptmt.setString(16, "" + mp.getString("tsf_flag"));/* 10：现金；11：转账 */
						ptmt.setString(16, "" + "11");/* 10：现金；11：转账 */
						ptmt.setString(17, "" + mp.getString("cur"));
//						ptmt.setString(18, "" + mp.getString("org_amt"));// 原币种交易金额
//						ptmt.setString(19, "" + mp.getString("usd_amt"));// 折美元交易金额
//						ptmt.setString(20, "" + mp.getString("rmb_amt"));// 折人民币交易金额
//						ptmt.setString(21, "" + mp.getString("balance"));// 账户余额
						ptmt.setBigDecimal(18, new BigDecimal(10000));// 原币种交易金额
						ptmt.setBigDecimal(19, new BigDecimal(10000/7.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 折美元交易金额
						ptmt.setBigDecimal(20, new BigDecimal(10000));// 折人民币交易金额
						ptmt.setBigDecimal(21, balance);// 账户余额
						ptmt.setString(22, "" + mp.getString("agency_flag"));
						ptmt.setString(23, "" + mp.getString("agent_name"));
						ptmt.setString(24, "" + mp.getString("agent_tel"));
						ptmt.setString(25, "" + mp.getString("agent_type"));
						ptmt.setString(26, "" + mp.getString("agent_no"));
						// ptmt.setString(27,""+mp.getString("ticd"));//业务流水号YW201909091000001
						ptmt.setString(27, "" + "YW20190909" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(28, "" + mp.getString("counter_no"));
//						ptmt.setString(29, "" + mp.getString("settle_type"));// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(29, "" +"1004");// 业务类型:1001:取现;1002:存款;1003:转入;1004:转出;1005:现金汇款;
						ptmt.setString(30, "" + mp.getString("reverse_flag"));
						ptmt.setString(31, "" + mp.getString("purpose"));
						ptmt.setString(32, "" + mp.getString("bord_flag"));
						ptmt.setString(33, "" + mp.getString("nation"));
						ptmt.setString(34, "" + mp.getString("bank_flag"));
						ptmt.setString(35, "" + mp.getString("ip_code"));
						ptmt.setString(36, "" + mp.getString("atm_code"));
						ptmt.setString(37, "" + mp.getString("bank_code"));
						ptmt.setString(38, "" + mp.getString("mac_info"));
						ywSum++;
						ptmt.addBatch();

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功6tb_acc_txn基于客户账户的交易数据表");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("插入失败6tb_acc_txn基于客户账户的交易数据表");
				}
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		
	}
	//7tb_cross_border跨境汇款交易数据表
	public void batchAddTb_cross_border(int total,String suffix){
		System.out.println("开始插入7tb_cross_border跨境汇款交易数据表");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select '20190911' `date`," + 
				"					'141135' `time`," + 
				"					'11' `lend_flag`/*10：收；11：付（客户角度）*/," + 
				"					'10' `tsf_flag`/*10：现金；11：转账（客户角度）*/," + 
				"					acc_type as `unit_flag`," + 
				"					cst_no `cst_no`," + 
				"					id_no `id_no`," + 
				"					self_acc_name `self_acc_name`," + 
				"					self_acc_no `self_acc_no`," + 
				"					card_no `card_no`," + 
				"					'@N' `self_add`," + 
				"					'yw201909091000001' `ticd`," + 
				"					'1234567890' `part_acc_no`," + 
				"					'tom' `part_acc_name`," + 
				"					'en' `part_nation`," + 
				"					'city' `part_city`," + 
				"					'address' `part_add`," + 
				"					'bank' `part_bank`," + 
				"					'decl123456789' `self_decl`," + 
				"					'cny' `cur`," + 
				"					720 `org_amt`," + 
				"					100 usd_amt," + 
				"					720 rmb_amt," + 
				"					'swift' mode," + 
				"					'附言' purpose," + 
				"					'12' agency_flag," + 
				"					'@N' agent_name," + 
				"					'@N' agent_tel," + 
				"					'@N' agent_type," + 
				"					'@N' agent_no" + 
				"					 from tb_acc"+suffix+" where acc_type1 = '11' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type1='11'";
		
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `tb_cross_border"+suffix+"` (Date,Time,Lend_flag,Tsf_flag,Unit_flag,Cst_no,Id_no,Self_acc_name,Self_acc_no,Card_no,Self_add,Ticd,Part_acc_no,Part_acc_name,Part_nation,Part_city,Part_add,Part_bank,Self_decl,Cur,Org_amt,Usd_amt,Rmb_amt,Mode,Purpose,Agency_flag,Agent_name,Agent_tel,Agent_type,Agent_no) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ywSum = 1000001;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					try {
						ptmt.setString(1,mp.getString("date")+"");
						ptmt.setString(2,mp.getString("time")+"");
						ptmt.setString(3,mp.getString("lend_flag")+"");
						ptmt.setString(4,mp.getString("tsf_flag")+"");
						ptmt.setString(5,mp.getString("unit_flag")+"");
						ptmt.setString(6,mp.getString("cst_no")+"");
						ptmt.setString(7,mp.getString("id_no")+"");
						ptmt.setString(8,mp.getString("self_acc_name")+"");
						ptmt.setString(9,mp.getString("self_acc_no")+"");
						ptmt.setString(10,mp.getString("card_no")+"");
						ptmt.setString(11,mp.getString("self_add")+"");
						//ptmt.setString(12,mp.getString("ticd")+"");
						ptmt.setString(12, "" + "YW20190910" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(13,mp.getString("part_acc_no")+"");
						ptmt.setString(14,mp.getString("part_acc_name")+"");
						ptmt.setString(15,mp.getString("part_nation")+"");
						ptmt.setString(16,mp.getString("part_city")+"");
						ptmt.setString(17,mp.getString("part_add")+"");
						ptmt.setString(18,mp.getString("part_bank")+"");
						ptmt.setString(19,mp.getString("self_decl")+"");
						ptmt.setString(20,mp.getString("cur")+"");
						ptmt.setString(21,mp.getString("org_amt")+"");
						ptmt.setString(22,mp.getString("usd_amt")+"");
						ptmt.setString(23,mp.getString("rmb_amt")+"");
						ptmt.setString(24,mp.getString("mode")+"");
						ptmt.setString(25,mp.getString("purpose")+"");
						ptmt.setString(26,mp.getString("agency_flag")+"");
						ptmt.setString(27,mp.getString("agent_name")+"");
						ptmt.setString(28,mp.getString("agent_tel")+"");
						ptmt.setString(29,mp.getString("agent_type")+"");
						ptmt.setString(30,mp.getString("agent_no")+"");
						ywSum++;
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功7tb_cross_border跨境汇款交易数据表");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败7tb_cross_border跨境汇款交易数据表");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}		
		
	//8tb_cred_txn信用卡账户金融交易数据表
	public void batchAddTb_cred_txn(int total,String suffix){
		System.out.println("开始插入8tb_cred_txn信用卡账户金融交易数据表");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select " + 
				"self_acc_no self_acc_no," + 
				"card_no card_no," + 
				"self_acc_name self_acc_name," + 
				"cst_no cst_no," + 
				"id_no id_no," + 
				"'20190912' date," + 
				"'091802' time," + 
				"'11' lend_flag/*10：收；11：付（客户角度）*/," + 
				"'11' tsf_flag/*10：现金；11：转账（客户角度）*/," + 
				"'CNY' cur," + 
				"'72' org_amt," + 
				"'10' usd_amt," + 
				"'72' rmb_amt," + 
				"'72' balance," + 
				"'摘要' purpose," + 
				"'大食堂' pos_owner," + 
				"'12' trans_type," + 
				"'@N' ip_code," + 
				"'12' bord_flag," + 
				"'CN' nation," + 
				"'建设银行' part_bank," + 
				"'1234567890111' part_acc_no," + 
				"'大食堂' part_acc_name," + 
				"'1007' settle_type," + 
				"'YW201909091000001' ticd " + 
				"from tb_acc"+suffix+" where acc_type1='14' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type1='14'";
		
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `tb_cred_txn"+suffix+"` (`self_acc_no`,`card_no`,`self_acc_name`,`cst_no`,`id_no`,`date`,`time`,`lend_flag`,`tsf_flag`,`cur`,`org_amt`,`usd_amt`,`rmb_amt`,`balance`,`purpose`,`pos_owner`,`trans_type`,`ip_code`,`bord_flag`,`nation`,`part_bank`,`part_acc_no`,`part_acc_name`,`settle_type`,`ticd`) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ywSum = 1000001;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					BigDecimal balance=new BigDecimal(0.0);//余额
					balance = balance.add(new BigDecimal(100));
					try {
						ptmt.setString(1,mp.getString("self_acc_no")+"");
						ptmt.setString(2,mp.getString("card_no")+"");
						ptmt.setString(3,mp.getString("self_acc_name")+"");
						ptmt.setString(4,mp.getString("cst_no")+"");
						ptmt.setString(5,mp.getString("id_no")+"");
						ptmt.setString(6,mp.getString("date")+"");
						ptmt.setString(7,mp.getString("time")+"");
						ptmt.setString(8,mp.getString("lend_flag")+"");
						ptmt.setString(9,mp.getString("tsf_flag")+"");
						ptmt.setString(10,mp.getString("cur")+"");
//						ptmt.setString(11,mp.getString("org_amt")+"");
//						ptmt.setString(12,mp.getString("usd_amt")+"");
//						ptmt.setString(13,mp.getString("rmb_amt")+"");
//						ptmt.setString(14,mp.getString("balance")+"");

						ptmt.setBigDecimal(11, new BigDecimal(100));// 原币种交易金额
						ptmt.setBigDecimal(12, new BigDecimal(100/7.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 折美元交易金额
						ptmt.setBigDecimal(13, new BigDecimal(100));// 折人民币交易金额
						ptmt.setBigDecimal(14, balance);// 账户余额
						ptmt.setString(15,mp.getString("purpose")+"");
						ptmt.setString(16,mp.getString("pos_owner")+"");
						ptmt.setString(17,mp.getString("trans_type")+"");
						ptmt.setString(18,mp.getString("ip_code")+"");
						ptmt.setString(19,mp.getString("bord_flag")+"");
						ptmt.setString(20,mp.getString("nation")+"");
						ptmt.setString(21,mp.getString("part_bank")+"");
						ptmt.setString(22,mp.getString("part_acc_no")+"");
						ptmt.setString(23,mp.getString("part_acc_name")+"");
						ptmt.setString(24,mp.getString("settle_type")+"");
						ptmt.setString(25,"YW20190911"+ywSum);//YW201909091000001
						ywSum++;
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功8tb_cred_txn信用卡账户金融交易数据表");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败8tb_cred_txn信用卡账户金融交易数据表");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}		
	
	//9tb_cash_remit现金汇款交易流水
	public void batchAddTb_cash_remit(int total,String suffix){
		System.out.println("开始插入9tb_cash_remit现金汇款交易流水");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select " + 
				"'20190912' date, '094848' time, bank_code1 self_bank_code,"
				+ "self_acc_name acc_name,"
				+ "id_no id_no,"
				+ "'CNY' cur,"
				+ "'100' org_amt,"
				+ "'100' usd_amt,"
				+ "'100' rmb_amt,"
				+ "'6223090000000000007' inside_acc_no,"
				+ "'JGWD102' part_bank_code,"
				+ "'中国工商银行' part_bank_name,"
				+ "'6223097120684107998' part_acc_no,"
				+ "'冰原制冷成套设备有限公司' part_acc_name,"
				+ "'YW201909100001' ticd,"
				+ "'GY0001' counter_no,"
				+ "'1005' settle_type"
				+ " from tb_acc"+suffix+" where acc_type1='11' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type1='11'";
		
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `tb_cash_remit"+suffix+"` (`date`,`time`,`self_bank_code`,`acc_name`,`id_no`,`cur`,`org_amt`,`usd_amt`,`rmb_amt`,`inside_acc_no`,`part_bank_code`,`part_bank_name`,`part_acc_no`,`part_acc_name`,`ticd`,`counter_no`,`settle_type`) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ywSum = 1000001;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					BigDecimal balance=new BigDecimal(0.0);//余额
					balance = balance.add(new BigDecimal(100));
					try {
						ptmt.setString(1,mp.getString("date")+"");
						ptmt.setString(2,mp.getString("time")+"");
						ptmt.setString(3,mp.getString("self_bank_code")+"");
						ptmt.setString(4,mp.getString("acc_name")+"");
						ptmt.setString(5,mp.getString("id_no")+"");
						ptmt.setString(6,mp.getString("cur")+"");
//						ptmt.setString(7,mp.getString("org_amt")+"");
//						ptmt.setString(8,mp.getString("usd_amt")+"");
//						ptmt.setString(9,mp.getString("rmb_amt")+"");
						ptmt.setBigDecimal(7, new BigDecimal(100));// 原币种交易金额
						ptmt.setBigDecimal(8, new BigDecimal(100/7.2).setScale(2, BigDecimal.ROUND_HALF_UP));// 折美元交易金额
						ptmt.setBigDecimal(9, new BigDecimal(100));// 折人民币交易金额
						ptmt.setString(10,mp.getString("inside_acc_no")+"");
						ptmt.setString(11,mp.getString("part_bank_code")+"");
						ptmt.setString(12,mp.getString("part_bank_name")+"");
						ptmt.setString(13,mp.getString("part_acc_no")+"");
						ptmt.setString(14,mp.getString("part_acc_name")+"");
//						ptmt.setString(15,mp.getString("ticd")+"");
						ptmt.setString(15,"YW20190912"+ywSum);//YW201909091000001
						ptmt.setString(16,mp.getString("counter_no")+"");
						ptmt.setString(17,mp.getString("settle_type")+"");

						ywSum++;
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功9tb_cash_remit现金汇款交易流水");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败9tb_cash_remit现金汇款交易流水");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}		
	
	//10tb_cash_convert现钞兑换交易明细表
	public void batchAddTb_cash_convert(int total,String suffix){
		System.out.println("开始插入10tb_cash_convert现钞兑换交易明细表");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select " + 
				"'20190912' date," + 
				"'101010' time," + 
				"bank_code1 self_bank_code," + 
				"self_acc_name acc_name," + 
				"id_no id_no," + 
				"'USD' out_cur," + 
				"100 out_org_amt," + 
				"100 out_usd_amt," + 
				"'CNY' in_cur," + 
				"720 in_org_amt," + 
				"100 in_usd_amt," + 
				"'' ticd," + 
				"'GY0011' counter_no," + 
				"'1006' settle_type " + 
				"from tb_acc"+suffix+" where acc_type1='11' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type1='11'";
		
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `tb_cash_convert"+suffix+"` (`date`,`time`,`self_bank_code`,`acc_name`,`id_no`,`out_cur`,`out_org_amt`,`out_usd_amt`,`in_cur`,`in_org_amt`,`in_usd_amt`,`ticd`,`counter_no`,`settle_type`) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ywSum = 1000001;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					BigDecimal balance=new BigDecimal(0.0);//余额
					balance = balance.add(new BigDecimal(100));
					try {
						ptmt.setString(1,mp.getString("date")+"");
						ptmt.setString(2,mp.getString("time")+"");
						ptmt.setString(3,mp.getString("self_bank_code")+"");
						ptmt.setString(4,mp.getString("acc_name")+"");
						ptmt.setString(5,mp.getString("id_no")+"");
						ptmt.setString(6,mp.getString("out_cur")+"");
						ptmt.setString(7,mp.getString("out_org_amt")+"");
						ptmt.setString(8,mp.getString("out_usd_amt")+"");
						ptmt.setString(9,mp.getString("in_cur")+"");
						ptmt.setString(10,mp.getString("in_org_amt")+"");
						ptmt.setString(11,mp.getString("in_usd_amt")+"");
//						ptmt.setString(12,mp.getString("ticd")+"");
						ptmt.setString(12,"YW20190909"+ywSum);//YW201909091000001
						ptmt.setString(13,mp.getString("counter_no")+"");
						ptmt.setString(14,mp.getString("settle_type")+"");
						
						ywSum++;
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功10tb_cash_convert现钞兑换交易明细表");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败10tb_cash_convert现钞兑换交易明细表");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}		
	
	//11tb_risk_new存量客户最新风险等级表
	public void batchAddTb_risk_new(int total,String tableName,String suffix){
		System.out.println("开始插入11tb_risk_new存量客户最新风险等级表");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select " + 
				"bank_code1 bank_code1," + 
				"cst_no cst_no," + 
				"self_acc_name self_acc_name," + 
				"id_no id_no," + 
				"acc_type acc_type," + 
				"'13' risk_code," + 
				"'20190910' time," + 
				"'98' norm " + 
				"from tb_acc"+suffix+" where acc_type1='11' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type1='11'";
		
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `"+tableName+suffix+"` (`bank_code1`,`cst_no`,`self_acc_name`,`id_no`,`acc_type`,`risk_code`,`time`,`norm`) VALUES "
				+ "(?,?,?,?,?,?,?,?);";
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					try {
						ptmt.setString(1,mp.getString("bank_code1")+"");
						ptmt.setString(2,mp.getString("cst_no")+"");
						ptmt.setString(3,mp.getString("self_acc_name")+"");
						ptmt.setString(4,mp.getString("id_no")+"");
						ptmt.setString(5,mp.getString("acc_type")+"");
						ptmt.setString(6,mp.getString("risk_code")+"");
						ptmt.setString(7,mp.getString("time")+"");
						ptmt.setString(8,mp.getString("norm")+"");
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功11tb_risk_new存量客户最新风险等级表");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败11tb_risk_new存量客户最新风险等级表");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}		
	//13tb_lwhc_log公民联网核查日志记录表
	public void batchAddTb_lwhc_log(int total,String suffix){
		System.out.println("开始插入13tb_lwhc_log公民联网核查日志记录表");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select " + 
				"tb_bank.bank_name bank_name," + 
				"tb_acc.bank_code1 bank_code2," + 
				"'20190912' date," + 
				"'112122' time," + 
				"tb_acc.self_acc_name name," + 
				"tb_acc.id_no id_no," + 
				"'11' result," + 
				"'GY0001' counter_no," + 
				"'核查部门' ope_line," + 
				"'10' mode," + 
				"'摘要' purpose " + 
				"from tb_acc"+suffix+" tb_acc join tb_bank"+suffix+" tb_bank on tb_acc.head_no = tb_bank.head_no where  acc_type1='11' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type1='11'";
		
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `tb_lwhc_log"+suffix+"` (`bank_name`,`bank_code2`,`date`,`time`,`name`,`id_no`,`result`,`counter_no`,`ope_line`,`mode`,`purpose`) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?);";
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					try {
						ptmt.setString(1,mp.getString("bank_name")+"");
						ptmt.setString(2,mp.getString("bank_code2")+"");
						ptmt.setString(3,mp.getString("date")+"");
						ptmt.setString(4,mp.getString("time")+"");
						ptmt.setString(5,mp.getString("name")+"");
						ptmt.setString(6,mp.getString("id_no")+"");
						ptmt.setString(7,mp.getString("result")+"");
						ptmt.setString(8,mp.getString("counter_no")+"");
						ptmt.setString(9,mp.getString("ope_line")+"");
						ptmt.setString(10,mp.getString("mode")+"");
						ptmt.setString(11,mp.getString("purpose")+"");
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功13tb_lwhc_log公民联网核查日志记录表");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败13tb_lwhc_log公民联网核查日志记录表");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}		
	//批量插入--14tb_lar_report大额交易报告明细
	public void batchAddTb_lar_report(int total,String suffix){
		System.out.println("开始插入14tb_lar_report大额交易报告明细");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select   " + 
				"'JYBM201909100' BPTC,  '1200013' CATP,  card_no CBCN,  '10' CBCT,  '@N' CCEI,  bank_tel CCTL,  '14' CFCT,  'JRJG102' CFIC,  '中国工商银行' CFIN,  'CHN123456' CFRC,  '110001' CITP,  1000000 CRAT,  '0501' CRCD,  1000000 CRMB,  '资金用途' CRPP,  'CNY' CRTP,  tb_acc.cst_no CSNM,  card_no CTAC,  address1 CTAR,  tb_acc.id_no CTID,  tb_acc.self_acc_name CTNM,  'CHN' CTNT,  '1' CTTN,  '99999' CTVC,  '138888.889' CUSD,  '0' DTTN,  'JRJG102' FINC,  '20190912' HTDT,  '@N' MIRS,  tb_acc.open_time+'090909' OATM,  '@N' OCBT,  'ATM0001' OCEC,  '@N' OCNM,  '15' OCTT,  '@N' OITP,  '@N' OOCT,  '@N' OTCD,  '@N' OTDT,  '@N' OTIC,  'BGJG102' RICD,  '00' RLFC,  '@N' ROTF,  '通过银联清算的交易' RPMN,  '02' RPMT,  '@N' TBID,  '@N' TBIT,  '@N' TBNM,  '@N' TBNT,  '62123456789010001' TCAC,  '620009' TCAT,  '773015976' TCID,  '610001' TCIT,  '冰原制冷成套设备有限公司' TCNM,  'YW201909100001' TICD,  'CHN123456' TRCD,  '0' TSCT,  '01' TSDR,  '20190910165234' TSTM,  '0' TSTN,  '14' TSTP,  '1' TTNM  " + 
				" from tb_acc"+suffix+" tb_acc,tb_cst_pers"+suffix+" tb_cst_pers where  tb_acc.cst_no = tb_cst_pers.cst_no and  acc_type='11' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from tb_acc"+suffix+" where acc_type='11'";
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `tb_lar_report"+suffix+"` (`BPTC`,`CATP`,`CBCN`,`CBCT`,`CCEI`,`CCTL`,`CFCT`,`CFIC`,`CFIN`,`CFRC`,`CITP`,`CRAT`,`CRCD`,`CRMB`,`CRPP`,`CRTP`,`CSNM`,`CTAC`,`CTAR`,`CTID`,`CTNM`,`CTNT`,`CTTN`,`CTVC`,`CUSD`,`DTTN`,`FINC`,`HTDT`,`MIRS`,`OATM`,`OCBT`,`OCEC`,`OCNM`,`OCTT`,`OITP`,`OOCT`,`OTCD`,`OTDT`,`OTIC`,`RICD`,`RLFC`,`ROTF`,`RPMN`,`RPMT`,`TBID`,`TBIT`,`TBNM`,`TBNT`,`TCAC`,`TCAT`,`TCID`,`TCIT`,`TCNM`,`TICD`,`TRCD`,`TSCT`,`TSDR`,`TSTM`,`TSTN`,`TSTP`,`TTNM`) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		int ywSum = 10000000;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					try {
						ywSum++;
						ptmt.setString(1,mp.getString("BPTC")+""+ywSum);
						ptmt.setString(2,mp.getString("CATP")+"");
						ptmt.setString(3,mp.getString("CBCN")+"");
						ptmt.setString(4,mp.getString("CBCT")+"");
						ptmt.setString(5,mp.getString("CCEI")+"");
						ptmt.setString(6,mp.getString("CCTL")+"");
						ptmt.setString(7,mp.getString("CFCT")+"");
						ptmt.setString(8,mp.getString("CFIC")+"");
						ptmt.setString(9,mp.getString("CFIN")+"");
						ptmt.setString(10,mp.getString("CFRC")+"");
						ptmt.setString(11,mp.getString("CITP")+"");
						ptmt.setString(12,mp.getString("CRAT")+"");
						ptmt.setString(13,mp.getString("CRCD")+"");
						ptmt.setString(14,mp.getString("CRMB")+"");
						ptmt.setString(15,mp.getString("CRPP")+"");
						ptmt.setString(16,mp.getString("CRTP")+"");
						ptmt.setString(17,mp.getString("CSNM")+"");
						ptmt.setString(18,mp.getString("CTAC")+"");
						ptmt.setString(19,mp.getString("CTAR")+"");
						ptmt.setString(20,mp.getString("CTID")+"");
						ptmt.setString(21,mp.getString("CTNM")+"");
						ptmt.setString(22,mp.getString("CTNT")+"");
						ptmt.setString(23,mp.getString("CTTN")+"");
						ptmt.setString(24,mp.getString("CTVC")+"");
						ptmt.setString(25,mp.getString("CUSD")+"");
						ptmt.setString(26,mp.getString("DTTN")+"");
						ptmt.setString(27,mp.getString("FINC")+"");
						ptmt.setString(28,mp.getString("HTDT")+"");
						ptmt.setString(29,mp.getString("MIRS")+"");
						ptmt.setString(30,mp.getString("OATM")+"");
						ptmt.setString(31,mp.getString("OCBT")+"");
						ptmt.setString(32,mp.getString("OCEC")+"");
						ptmt.setString(33,mp.getString("OCNM")+"");
						ptmt.setString(34,mp.getString("OCTT")+"");
						ptmt.setString(35,mp.getString("OITP")+"");
						ptmt.setString(36,mp.getString("OOCT")+"");
						ptmt.setString(37,mp.getString("OTCD")+"");
						ptmt.setString(38,mp.getString("OTDT")+"");
						ptmt.setString(39,mp.getString("OTIC")+"");
						ptmt.setString(40,mp.getString("RICD")+"");
						ptmt.setString(41,mp.getString("RLFC")+"");
						ptmt.setString(42,mp.getString("ROTF")+"");
						ptmt.setString(43,mp.getString("RPMN")+"");
						ptmt.setString(44,mp.getString("RPMT")+"");
						ptmt.setString(45,mp.getString("TBID")+"");
						ptmt.setString(46,mp.getString("TBIT")+"");
						ptmt.setString(47,mp.getString("TBNM")+"");
						ptmt.setString(48,mp.getString("TBNT")+"");
						ptmt.setString(49,mp.getString("TCAC")+"");
						ptmt.setString(50,mp.getString("TCAT")+"");
						ptmt.setString(51,mp.getString("TCID")+"");
						ptmt.setString(52,mp.getString("TCIT")+"");
						ptmt.setString(53,mp.getString("TCNM")+"");
						//ptmt.setString(54,mp.getString("TICD")+"");
						ptmt.setString(54,"YW20190909" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(55,mp.getString("TRCD")+"");
						ptmt.setString(56,mp.getString("TSCT")+"");
						ptmt.setString(57,mp.getString("TSDR")+"");
						ptmt.setString(58,mp.getString("TSTM")+"");
						ptmt.setString(59,mp.getString("TSTN")+"");
						ptmt.setString(60,mp.getString("TSTP")+"");
						ptmt.setString(61,mp.getString("TTNM")+"");
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功14tb_lar_report大额交易报告明细");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败14tb_lar_report大额交易报告明细");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}	
	//批量插入--15tb_sus_report可疑交易报告明细
	public void batchAddTb_sus_report(int total,String suffix){
		System.out.println("开始插入15tb_sus_report可疑交易报告明细");
		PreparedStatement ptmt = null;
		ResultSet mp = null;
		int mycount = 0;
		//要执行的SQL语句
		String sql_data = "select '疑点分析' AOSP," + 
				"'JYBM201909100' BPTC," + 
				"'@N' CATM," + 
				"'1200013' CATP," + 
				"card_no CBCN," + 
				"'10' CBCT," + 
				"'14' CFCT," + 
				"'JRJG102' CFIC," + 
				"'中国工商银行' CFIN," + 
				"'CHN123456' CFRC," + 
				"'110001' CITP," + 
				"1000000 CRAT," + 
				"'资金用途' CRSP," + 
				"'CNY' CRTP," + 
				"tb_acc.cst_no CSNM," + 
				"card_no CTAC," + 
				"tb_acc.id_no CTID," + 
				"tb_acc.self_acc_name CTNM," + 
				"'01' DETR," + 
				"'01' DORP," + 
				"'JRJG102' FINC," + 
				"'@N' MIRS," + 
				"tb_acc.open_time+'090909' OATM," + 
				"'@N' OCBT," + 
				"'ATM0001' OCEC," + 
				"'110001' OCIT," + 
				"'15' OCTT," + 
				"'@N' ODRP," + 
				"'@N' OITP," + 
				"'15' OOCT," + 
				"'110001' ORIT," + 
				"'@N' ORXN," + 
				"'@N' OTPR," + 
				"'BGJG102' RICD," + 
				"'00' RLFC," + 
				"'@N' ROTF," + 
				"'通过银联清算的交易' RPMN," + 
				"'李易联' RPNM," + 
				"'02' RPMT," + 
				"'JRJG102' RPNC," + 
				"tb_acc.id_no SCID," + 
				"'110001' SCIT," + 
				"self_acc_name SCNM," + 
				"bank_tel SCTL," + 
				"address1 SEAR," + 
				"'@N' SEEI," + 
				"tb_acc.id_no SEID," + 
				"self_acc_name SENM," + 
				"'1' SETN," + 
				"'110001' SETP," + 
				"'99999' SEVC," + 
				"tb_acc.id_no SRID," + 
				"'110001' SRIT," + 
				"self_acc_name SRNM," + 
				"'资金交易及客户行为情况' STCB," + 
				"'可疑交易特征代码' STCR," + 
				"'1' STNM," + 
				"'CN' STNT," + 
				"'@N' TBID," + 
				"'@N' TBIT," + 
				"'@N' TBNM," + 
				"'@N' TBNT," + 
				"'62123456789010001' TCAC," + 
				"'620009' TCAT," + 
				"'773015976' TCID," + 
				"'610001' TCIT," + 
				"'冰原制冷成套设备有限公司' TCNM," + 
				"'YW201909100001' TICD," + 
				"'1' TORP," + 
				"'0502' TOSC," + 
				"'02' TPTR," + 
				"'CHN123456' TRCD," + 
				"'0' TSCT," + 
				"'01' TSDR," + 
				"'20190910165234' TSTM," + 
				"'14' TSTP" + 
				" from  tb_acc"+suffix+" tb_acc,tb_cst_pers"+suffix+" tb_cst_pers where  tb_acc.cst_no = tb_cst_pers.cst_no and  acc_type='11' limit ?,"+perNum;
		String sql_recodeCount = "select count(1) as mycount from  tb_acc"+suffix+" tb_acc,tb_cst_pers"+suffix+" tb_cst_pers where  tb_acc.cst_no = tb_cst_pers.cst_no and  acc_type='11'";
		try {
			ptmt = connection.prepareStatement(sql_recodeCount);
			mp = ptmt.executeQuery();
			mp.next();
			mycount = mp.getInt("mycount");
			mp.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ptmt = null;
		String sql="INSERT INTO `tb_sus_report"+suffix+"` (`AOSP`,`BPTC`,`CATM`,`CATP`,`CBCN`,`CBCT`,`CFCT`,`CFIC`,`CFIN`,`CFRC`,`CITP`,`CRAT`,`CRSP`,`CRTP`,`CSNM`,`CTAC`,`CTID`,`CTNM`,`DETR`,`DORP`,`FINC`,`MIRS`,`OATM`,`OCBT`,`OCEC`,`OCIT`,`OCTT`,`ODRP`,`OITP`,`OOCT`,`ORIT`,`ORXN`,`OTPR`,`RICD`,`RLFC`,`ROTF`,`RPMN`,`RPNM`,`RPMT`,`RPNC`,`SCID`,`SCIT`,`SCNM`,`SCTL`,`SEAR`,`SEEI`,`SEID`,`SENM`,`SETN`,`SETP`,`SEVC`,`SRID`,`SRIT`,`SRNM`,`STCB`,`STCR`,`STNM`,`STNT`,`TBID`,`TBIT`,`TBNM`,`TBNT`,`TCAC`,`TCAT`,`TCID`,`TCIT`,`TCNM`,`TICD`,`TORP`,`TOSC`,`TPTR`,`TRCD`,`TSCT`,`TSDR`,`TSTM`,`TSTP`) VALUES "
				+ "(?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?,	?);";
		int ywSum = 100;
		for (int myPage = 1; myPage <= (mycount / perNum); myPage++) {
			
			try {
				ptmt = connection.prepareStatement(sql_data);
				ptmt.setLong(1, myPage);
				mp = ptmt.executeQuery();
				try {
					connection.setAutoCommit(false);// 关闭事务
					ptmt = connection.prepareStatement(sql);

				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				while (mp.next()) {
					try {
						ywSum++;
						ptmt.setString(1,mp.getString("AOSP")+"");
//						ptmt.setString(2,mp.getString("BPTC")+"");
						ptmt.setString(2,mp.getString("BPTC")+""+ywSum);
						ptmt.setString(3,mp.getString("CATM")+"");
						ptmt.setString(4,mp.getString("CATP")+"");
						ptmt.setString(5,mp.getString("CBCN")+"");
						ptmt.setString(6,mp.getString("CBCT")+"");
						ptmt.setString(7,mp.getString("CFCT")+"");
						ptmt.setString(8,mp.getString("CFIC")+"");
						ptmt.setString(9,mp.getString("CFIN")+"");
						ptmt.setString(10,mp.getString("CFRC")+"");
						ptmt.setString(11,mp.getString("CITP")+"");
						ptmt.setString(12,mp.getString("CRAT")+"");
						ptmt.setString(13,mp.getString("CRSP")+"");
						ptmt.setString(14,mp.getString("CRTP")+"");
						ptmt.setString(15,mp.getString("CSNM")+"");
						ptmt.setString(16,mp.getString("CTAC")+"");
						ptmt.setString(17,mp.getString("CTID")+"");
						ptmt.setString(18,mp.getString("CTNM")+"");
						ptmt.setString(19,mp.getString("DETR")+"");
						ptmt.setString(20,mp.getString("DORP")+"");
						ptmt.setString(21,mp.getString("FINC")+"");
						ptmt.setString(22,mp.getString("MIRS")+"");
						ptmt.setString(23,mp.getString("OATM")+"");
						ptmt.setString(24,mp.getString("OCBT")+"");
						ptmt.setString(25,mp.getString("OCEC")+"");
						ptmt.setString(26,mp.getString("OCIT")+"");
						ptmt.setString(27,mp.getString("OCTT")+"");
						ptmt.setString(28,mp.getString("ODRP")+"");
						ptmt.setString(29,mp.getString("OITP")+"");
						ptmt.setString(30,mp.getString("OOCT")+"");
						ptmt.setString(31,mp.getString("ORIT")+"");
						ptmt.setString(32,mp.getString("ORXN")+"");
						ptmt.setString(33,mp.getString("OTPR")+"");
						ptmt.setString(34,mp.getString("RICD")+"");
						ptmt.setString(35,mp.getString("RLFC")+"");
						ptmt.setString(36,mp.getString("ROTF")+"");
						ptmt.setString(37,mp.getString("RPMN")+"");
						ptmt.setString(38,mp.getString("RPNM")+"");
						ptmt.setString(39,mp.getString("RPMT")+"");
						ptmt.setString(40,mp.getString("RPNC")+"");
						ptmt.setString(41,mp.getString("SCID")+"");
						ptmt.setString(42,mp.getString("SCIT")+"");
						ptmt.setString(43,mp.getString("SCNM")+"");
						ptmt.setString(44,mp.getString("SCTL")+"");
						ptmt.setString(45,mp.getString("SEAR")+"");
						ptmt.setString(46,mp.getString("SEEI")+"");
						ptmt.setString(47,mp.getString("SEID")+"");
						ptmt.setString(48,mp.getString("SENM")+"");
						ptmt.setString(49,mp.getString("SETN")+"");
						ptmt.setString(50,mp.getString("SETP")+"");
						ptmt.setString(51,mp.getString("SEVC")+"");
						ptmt.setString(52,mp.getString("SRID")+"");
						ptmt.setString(53,mp.getString("SRIT")+"");
						ptmt.setString(54,mp.getString("SRNM")+"");
						ptmt.setString(55,mp.getString("STCB")+"");
						ptmt.setString(56,mp.getString("STCR")+"");
						ptmt.setString(57,mp.getString("STNM")+"");
						ptmt.setString(58,mp.getString("STNT")+"");
						ptmt.setString(59,mp.getString("TBID")+"");
						ptmt.setString(60,mp.getString("TBIT")+"");
						ptmt.setString(61,mp.getString("TBNM")+"");
						ptmt.setString(62,mp.getString("TBNT")+"");
						ptmt.setString(63,mp.getString("TCAC")+"");
						ptmt.setString(64,mp.getString("TCAT")+"");
						ptmt.setString(65,mp.getString("TCID")+"");
						ptmt.setString(66,mp.getString("TCIT")+"");
						ptmt.setString(67,mp.getString("TCNM")+"");
//						ptmt.setString(68,mp.getString("TICD")+"");
						ptmt.setString(68,"YW20190909" + ywSum);// 业务流水号YW201909091000001
						ptmt.setString(69,mp.getString("TORP")+"");
						ptmt.setString(70,mp.getString("TOSC")+"");
						ptmt.setString(71,mp.getString("TPTR")+"");
						ptmt.setString(72,mp.getString("TRCD")+"");
						ptmt.setString(73,mp.getString("TSCT")+"");
						ptmt.setString(74,mp.getString("TSDR")+"");
						ptmt.setString(75,mp.getString("TSTM")+"");
						ptmt.setString(76,mp.getString("TSTP")+"");
						ptmt.addBatch();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					ptmt.executeBatch();// 执行给定的SQL语句，该语句可能返回多个结果
					connection.commit();
					System.out.println("插入成功15tb_sus_report可疑交易报告明细");
					ptmt.clearBatch();
					mp.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println("插入失败15tb_sus_report可疑交易报告明细");
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		}
	}	
	//查询--0tb_bank银行表
	public List selectTableList(String tableName){
		List list = null;
		String sql="select * from "+tableName;
		try {
			Statement ptmt = connection.createStatement();
			ResultSet rs = ptmt.executeQuery(sql);
			list = ResultSetToList(rs);
			rs.close();
			ptmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//创建表
	public void createTable(String head_no){
		String[] tableType = new String[] {"tb_bank","tb_cst_unit","tb_settle_type",
				"tb_cst_pers","tb_acc","tb_acc_txn","tb_cross_border","tb_cred_txn","tb_cash_remit","tb_cash_convert",
				"tb_risk_new","tb_risk_his","tb_lwhc_log","tb_lar_report","tb_sus_report"};
		String[] sql = new String[tableType.length];
		String[] sql2 = new String[tableType.length];
		try {
			connection.setAutoCommit(false);// 关闭事务
			Statement statement = connection.createStatement();
			for(int i=0;i<tableType.length;i++) {
				sql[i] = "create table if not exists "+tableType[i]+head_no+" as (select * from "+tableType[i]+")";
				sql2[i] = "insert into tb_hbank_table(`table_no`,`table_type`,`head_no`,`table_name`,`create_time`) values "+
				"('"+(head_no+i)+"','"+tableType[i]+"','"+head_no+"','"+tableType[i]+head_no+"','')";
				statement.addBatch(sql[i]);
				statement.addBatch(sql2[i]);
			}
			statement.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
