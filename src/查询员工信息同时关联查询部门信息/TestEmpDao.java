package 查询员工信息同时关联查询部门信息;

public class TestEmpDao {

	public static void main(String[] args) {
		EmpDao dao=new EmpDao();
		
		Emp emp=dao.queryEmpAndDeptmessage(7902);
		
		System.out.println(emp.getDept().getDname());
	}

}
