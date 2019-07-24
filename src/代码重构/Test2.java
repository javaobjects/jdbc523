package 代码重构;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
* <p>Title: Test2</p>  
* <p>
*	Description: 
*	查询所有员工的总工资
* </p> 
* @author xianxian 
* @date 2019年7月24日
 */
public class Test2 {

	public static void main(String[] args) {
		/**
		 * 套路：
		 * 1. 创建连接对象
		 * 2. 创建执行sql语句的命令对象
		 * 3. 执行sql语句
		 * 4. 如果是查询获取结果集，如果是更新获取影响的行数
		 * 5. 如果是查询，需要从结果集获取数据
		 * 6. 关闭所有资源
		 */
		//1.创建连接对象
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","scott123");
			conn = DBUtil.getConnection();
			//2. 创建执行sql语句的命令对象
			stmt = conn.createStatement();
			//3. 执行sql语句
			String query_sum_sal = "select sum(sal) as sum_sal from emp";
			//4. 执行sql语句、如果是查询获取结果集
			rs = stmt.executeQuery(query_sum_sal);
			//5. 如果是查询，需要从结果集获取数据
			if(rs.next()) {
				Double sal = rs.getDouble("sum_sal");//sum_sal 是结果集中的列名
				System.out.println(sal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//6. 关闭所有资源
			DBUtil.release(conn, stmt, rs);
//			try {
//				if(rs != null) {
//					rs.close();
//				}
//				if(stmt != null) {
//					stmt.close();
//				}
//				if(conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
	}
}
