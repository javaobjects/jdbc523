package 参数配置化;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 连接数据库的工具类
 * @author hp
 *
 */
public class DBUtil {
	
	private static String driverName;
	private static String url;
	private static String username;
	private static String password;
	
	static
	{
		//如何读取属性文件：jdbc.properties
		//使用的技术：使用类加载器获取输入流进而加载属性文件，拿到其中的数据
		InputStream in=DBUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties prop=new Properties();
		
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driverName=prop.getProperty("jdbc_driver");
		url=prop.getProperty("jdbc_url");
		username=prop.getProperty("jdbc_username");
		password=prop.getProperty("jdbc_password");
		
	}
	
	//获取连接对象的方法
	public static Connection getConnection()
	{
		Connection conn=null;
		try {
			Class.forName(driverName);
			conn=DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//释放资源的方法
	

}
