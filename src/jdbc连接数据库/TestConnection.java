package jdbc连接数据库;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

	public static void main(String[] args) {
		try {
			//第一步：加载驱动类
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//获取连接对象
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","scott123");
			System.out.println("conn == null?" + (conn == null) + ": " +conn);//conn == null?false: oracle.jdbc.driver.T4CConnection@27f8302d
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
