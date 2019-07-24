package sql注入;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import 封装dao.DBUtil;

public class Test {
	/**
	 * 演示登录场景：输入用户名和密码，然后程序执行一条sql语句：
	 * 1.能够轻松欺骗服务的方法：
	 * 2.不能欺骗服务的方法
	 * 
	 * 知识点：
	 * Statement 和 PreparedStatement的区别：
	 * 1.PreparedStatement是预编译的，就是sql语句在给占位符赋值之前会提前编译好
	 * 2.你的参数ename="jones' or 1='1"，那么PreparedStatement就认为它是一个值，不会对其中的内容进行解析，比如中间有个or，它不会解析
	 * 3.以后建议大家使用预编译的PreparedStatement，不但效率高，能够重用，而且安全
	 */
	//1.能够轻松欺骗服务的方法：
	public Emp queryEmpByEnameAndPassword(Integer password,String ename)
	{
		Emp emp=null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select empno,ename,job,hiredate,sal from emp "
					+ "where empno="+password+" and ename='"+ename+"'");
			//4.把结果集中的数据转化为Emp对象
			//目标是把结果集中的数据取出来放入Emp对象中
			emp=new Emp();
			if(rs.next()){//确认结果集中只有一条数据，所以只需要游标移动一次
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getDouble("sal"));
				emp.setHiredate(rs.getDate("hiredate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtil.release(conn, stmt, rs);
		}
		return emp;
	}
	
	//2.能够有效防止sql注入的方法
	public Emp queryEmpByEnameAndPassword_security(Integer password,String ename)
	{
		Emp emp=null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConnection();
			/*stmt=conn.createStatement();
			rs=stmt.executeQuery("select empno,ename,job,hiredate,sal from emp "
					+ "where empno="+password+" and ename='"+ename+"'");*/
			stmt=conn.prepareStatement("select empno,ename,job,hiredate,sal from emp where empno=? and ename=?");
			stmt.setInt(1, password);
			stmt.setString(2, ename);
			
			rs=stmt.executeQuery();
			//4.把结果集中的数据转化为Emp对象
			//目标是把结果集中的数据取出来放入Emp对象中
			
			if(rs.next()){//确认结果集中只有一条数据，所以只需要游标移动一次
				emp=new Emp();
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getDouble("sal"));
				emp.setHiredate(rs.getDate("hiredate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtil.release(conn, stmt, rs);
		}
		return emp;
	}

	public static void main(String[] args) {
		// sql注入演示
		//假设用户登录时输入的用户名和密码如下：
		Integer password=7566;
		String ename="jones' or 1='1";//select * from emp where password=7566 and ename='jones' or 1='1'
		
		Test test=new Test();
		/*Emp emp=test.queryEmpByEnameAndPassword(password, ename);
		System.out.println(emp);*/
		
		Emp emp=test.queryEmpByEnameAndPassword_security(password, ename);
		System.out.println(emp);
	}
}
