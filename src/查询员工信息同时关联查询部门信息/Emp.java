package 查询员工信息同时关联查询部门信息;

import java.util.Date;

public class Emp {
	
	private Integer empno;
	private String ename;
	private String job;
	private Double sal;
	private Date hiredate;
	
	private Dept dept;
	
	
	
	//记住规律：如果两个类有一对多或者多对一的关系，那么90%的情况我们让多的一方维护关系
	/*
	 1-many:dept-->emp
	 many-1:emp--->dept
	 */
	
	public Dept getDept() {
		return dept;
	}


	public void setDept(Dept dept) {
		this.dept = dept;
	}


	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename + ", job=" + job
				+ ", sal=" + sal + ", hiredate=" + hiredate + "]\n";
	}


	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Emp(Integer empno, String ename, String job, Double sal,
			Date hiredate) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.sal = sal;
		this.hiredate = hiredate;
	}
	public Emp(Integer empno, String ename, String job, Date hiredate) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.hiredate = hiredate;
	}
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public Double getSal() {
		return sal;
	}
	public void setSal(Double sal) {
		this.sal = sal;
	}
	
	

	/*
	 * create table EMP
(
  empno    NUMBER(4) not null,
  ename    VARCHAR2(10),
  job      VARCHAR2(9),
  mgr      NUMBER(4),
  hiredate DATE,
  sal      NUMBER(7,2),
  comm     NUMBER(7,2),
  deptno   NUMBER(2)
)
	 */
	
}
