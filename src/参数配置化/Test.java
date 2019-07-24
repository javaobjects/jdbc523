package 参数配置化;

import java.sql.Connection;

public class Test {

	public static void main(String[] args) {

		Connection conn=DBUtil.getConnection();
		
		System.out.println(conn);
	}

}
