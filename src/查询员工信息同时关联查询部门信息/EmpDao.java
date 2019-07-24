package 查询员工信息同时关联查询部门信息;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class EmpDao {
	
	/**
	 * 查询单个员工信息同时关联查询部门信息
	 * @param empno
	 * @return
	 */
	public Emp queryEmpAndDeptmessage(Integer empno)
	{
		Emp emp = null;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=DBUtil.getConnection();
			stmt=conn.prepareStatement("select e.empno,e.ename,e.job,e.hiredate,e.sal,d.dname from emp e,dept d "
					+ "where e.deptno=d.deptno and e.empno=?");
			stmt.setInt(1, empno);
			
			rs=stmt.executeQuery();
			if(rs.next()){
				emp=new Emp();
				emp.setEmpno(rs.getInt("empno"));
				emp.setEname(rs.getString("ename"));
				emp.setJob(rs.getString("job"));
				emp.setSal(rs.getDouble("sal"));
				emp.setHiredate(rs.getDate("hiredate"));
				
				Dept dept=new Dept();
				dept.setDname(rs.getString("dname"));
				emp.setDept(dept);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			DBUtil.release(conn, stmt, rs);
		}
		return emp;
	}

}
