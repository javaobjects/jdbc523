package 封装dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
* <p>Title: DBUtil</p>  
* <p>
*	Description:
*	连接数据库的工具类 
* </p> 
* @author xianxian 
* @date 2019年7月24日
 */
public class DBUtil {

	//获取Connection连接的方法
	//经常用的方法一般是公共的静态的 public static
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","scott123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	//释放资源的方法
	public static void release(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
