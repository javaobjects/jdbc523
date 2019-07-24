package 封装dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
* <p>Title: EmpDao</p>  
* <p>
*	Description: 
*	对emp表进行增删改查的数据访问对象
* </p> 
* @author xianxian 
* @date 2019年7月24日
 */
public class EmpDao {
//	1.根据id查询某个员工信息
	public Emp queryEmpByEmpno(Integer empno) {
		Emp emp = null;
		
		/**
		 * 1.连接数据库
		 * 2.Statement操作对象传送sql语句到数据库并执行
		 * 3.获取结果集ResultSet
		 * 4.把结果集中的数据转化为Emp对象
		 */
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select empno,ename,job,hiredate,sal from emp where empno = " + empno);
			//4.把结果集中的数据转化为Emp对象
			//目标是把结果集中的数据取出来放入Emp对象中
			emp = new Emp();
			if(rs.next()) {//确认结果集中只有一条数据，所以只需要游标移动一次
				System.out.println(rs.getDouble("sal"));//括号中的sal来自结果集，是列名
				System.out.println(rs.getString("ename"));
				System.out.println(rs.getInt("empno"));
				System.out.println(rs.getString("job"));
				System.out.println(rs.getDate("hiredate"));
				
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getDouble("sal"));
				emp.setHiredate(rs.getDate("hiredate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.release(conn, stmt, rs);
		}
		return emp;
	}
//	2.根据id查询某个员工的工资  √
	public Double querySalByEmpno(Integer empno) {
		Double sal = null;
		return sal;
	}

	
//	3.查询所有员工的总工资 √
	public Double querySumSal() {
		Double sal = null;
		return sal;
	}

	
//	4.根据所有员工信息
	public List<Emp> queryAllEmp(){
		List<Emp> list = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select empno,ename,job,sal,hiredate from emp");
			//如何把数据从结果集中取出来，放入list集合呢？
			while(rs.next()) {//确认结果集中只有一条数据，就要使用while循环
//				System.out.println(rs.getDouble("sal"));//括号中的sal来自结果集，是列名
//				System.out.println(rs.getString("ename"));
//				System.out.println(rs.getInt("empno"));
//				System.out.println(rs.getString("job"));
//				System.out.println(rs.getDate("hiredate"));
				
				Emp emp = new Emp();
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getDouble("sal"));
				emp.setHiredate(rs.getDate("hiredate"));
				
				list.add(emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.release(conn, stmt, rs);
		}
		return list;
	}

//	5.查询工资前五名的员工信息
	/**
	 * 查询工资前五名的员工信息
	 * select * from emp e where(select count(1) from emp where sal > e.sal) <= 4;
	 */
	public List<Emp> queryEmpLimitFive(){
		List<Emp> list = null;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtil.getConnection();
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery("select empno,ename,sal,hiredate,job from emp e "
					+ "where (select count(1) from emp where sal>e.sal)<=4");
			//如何把数据从结果集中取出来放入list集合呢？
			
			while(rs.next()){//确认结果集中有多于一条数据时，就要使用while循环
				
				Emp emp=new Emp();
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getDouble("sal"));
				emp.setHiredate(rs.getDate("hiredate"));
				
				list.add(emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtil.release(conn, stmt, rs);
		}
		return list;
	}

//	6.根据id删除某个员工
	public int deleteEmpByEmpno(Integer empno) {
		int rows = 0;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.createStatement();
			String delete_emp_by_empno = "delete from emp where empno = " + empno;
			rows = stmt.executeUpdate(delete_emp_by_empno);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.release(conn, stmt, null);
		}
		return rows;
	}

	public boolean deleteEmp_ok(Integer[] empnos) {
		boolean tmp = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DBUtil.getConnection();
			//设置事务提交为手动
			conn.setAutoCommit(false);
			
			stmt = conn.prepareStatement("delete from emp where empno = ?");
			int rows = 0;
			for (int i = 0; i < empnos.length; i++) {
				stmt.setInt(1,empnos[i]);
				rows += stmt.executeUpdate();
				/*if(rows==1)
				{
					int a=10/0;
				}*/
			}
			if(rows == empnos.length) {
				tmp = true;
				//如果删除影响的行数等于数组长度，表示每个删除都有执行，所以事务是成功的，所以事务提交
				conn.commit();
			}else {
				conn.rollback();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			try {
				//如果代码抛出异常，有的删除执行有的没有执行，那么事务回滚
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			DBUtil.release(conn, stmt, null);
		}
		return tmp;
	}
	// 7.批量删除(executeBatch方法是提交批处理的命令，
	//返回一个整形数组int[]，数组中的每个数字对应一条命令的影响行数，
	//在Oracle的驱动中没有实现该功能，即提交成功后不能返回影响行数，所以返回-2
	public boolean deleteEmp(Integer[] empnos) {
		boolean result = false;
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		String sql = "delete from emp where empno = ?";
//		try {
//			conn = DBUtil.getConnection();
//			stmt = conn.prepareStatement(sql);
//			
//			for (Integer id:empnos) {
//				stmt.setInt(1, id);//给sql语句中的问号（占位符）赋值，1表示索引，id是值
//				stmt.addBatch();//批处理的意思，因为sql语句要执行多次，那么一次执行完
//			}
//			
//			int[] a = stmt.executeBatch();
//			System.out.println(Arrays.toString(a));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return result;
	}

//	8.更新某个员工的信息
	public int updateEmp(Emp emp) {
		int rows = 0;
		
		Connection conn=null;
		PreparedStatement stmt=null;
		
		try {
			conn=DBUtil.getConnection();
			stmt=conn.prepareStatement("update emp set ename=?,job=?,sal=?,hiredate=? where empno=?");
			/*
			 * 注意：在执行更新的sql语句之前，需要先给占位符赋值
			 */
	        stmt.setString(1, emp.getEname());
			stmt.setString(2,emp.getJob());
			stmt.setDouble(3, emp.getSal());
			/*Date date=emp.getHiredate();
			long time=date.getTime();
			java.sql.Date date2=new java.sql.Date(time);*/
			stmt.setDate(4,new java.sql.Date(emp.getHiredate().getTime()));
			stmt.setInt(5,emp.getEmpno());
			
			/*
			 * 执行更新
			 */
			rows=stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtil.release(conn, stmt, null);
		}
		
		return rows;
	}

//	9.添加一个员工信息
	public int addEmp(Emp emp) {
		int rows = 0;
		
		Connection conn=null;
		PreparedStatement stmt=null;
		
		try {
			conn=DBUtil.getConnection();
			stmt=conn.prepareStatement("insert into emp (ename,job,sal,hiredate,empno) values (?,?,?,?,?)");
			/*
			 * 注意：在执行更新的sql语句之前，需要先给占位符赋值
			 */
	        stmt.setString(1, emp.getEname());
			stmt.setString(2,emp.getJob());
			stmt.setDouble(3, emp.getSal());
			/*Date date=emp.getHiredate();
			long time=date.getTime();
			java.sql.Date date2=new java.sql.Date(time);*/
			stmt.setDate(4,new java.sql.Date(emp.getHiredate().getTime()));
			stmt.setInt(5,emp.getEmpno());
			
			/*
			 * 执行更新
			 */
			rows=stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtil.release(conn, stmt, null);
		}
		
		return rows;
	}
}
