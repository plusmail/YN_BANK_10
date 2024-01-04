package com.yi.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import jakarta.swing.JOptionPane;

import com.yi.dao.EmployeeDao;
import com.yi.dto.Customer;
import com.yi.dto.Department;
import com.yi.dto.Employee;
import com.yi.dto.Plan;
import com.yi.handler.paging.SearchCriteria;

public class EmployeeDaoImpl implements EmployeeDao {
	String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();
	private EmployeeDaoImpl() {};
	
	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Employee getEmpIdPass(Employee emp) throws SQLException {
		String sql ="SELECT empname FROM employee WHERE empid = ? AND emppwd = SHA2(?, 256)";
//		String sql = "select empname from employee where empid=? and emppwd=password(?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpPwd());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					return getEmployeeByLogin(rs);
				}
			}
		}
		return null;
	}

	private Employee getEmployeeByLogin(ResultSet rs) throws SQLException {
		Employee emp = new Employee();
		emp.setEmpName(rs.getString("empname"));
		return emp;
	}

	@Override
	public Employee getEmpAuth(Employee emp) throws SQLException {
		String sql = "select empCode, empname, empauth, empTitle, pic from employee where empname = ?";
		Employee employee = new Employee();
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, emp.getEmpName());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					employee = getEmployeeAuth(rs);
				}
			}
		}
		return employee;
	}

	private Employee getEmployeeAuth(ResultSet rs) throws SQLException {
		Employee emp = new Employee();
		emp.setEmpCode(rs.getString("empCode"));
		emp.setEmpName(rs.getString("empname"));
		emp.setEmpAuth(rs.getString("empauth"));
		emp.setEmpTitle(rs.getString("empTitle"));
		emp.setPic(rs.getString("pic"));
		return emp;
	}

	@Override
	public List<Employee> selectEmpByNameList(String empName, int empRetired) throws SQLException {
		//Employee emp = null;
		List<Employee> list = new ArrayList<Employee>();
		String sql = "select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
				"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
				"   where empName like ? and empRetire=?";
		
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, "%"+empName+"%");
			pstmt.setInt(2, empRetired);
			
			try(ResultSet rs = pstmt.executeQuery();){
				while(rs.next()) {
					list.add(getEmployee(rs));
					
					//return getEmployee(rs);
				}
				return list;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insertEmployee(Employee emp) {
		String sql = "insert into employee values(?,?,?,?,?,?,?,password(?),?,?,0)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt= con.prepareStatement(sql)){
			pstmt.setString(1, emp.getEmpCode());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpTitle());
			pstmt.setString(4, emp.getEmpAuth());
			pstmt.setInt(5, emp.getEmpSalary());
			pstmt.setString(6, emp.getEmpTel());
			pstmt.setString(7, emp.getEmpId());
			pstmt.setString(8, emp.getEmpPwd());
			pstmt.setInt(9, emp.getDept().getDeptNo());
			pstmt.setString(10, emp.getPic());
			
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();

		}
		
		return 0;
	}

	@Override
	public int updateEmployee(Employee emp) {
		String sql="update employee set empName=?,empTitle=?,empAuth=?,empSalary=?,empTel=?,empId=?,empPwd=password(?),deptNo=?,pic=? where empCode=?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt= con.prepareStatement(sql)){
			
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getEmpTitle());
			pstmt.setString(3, emp.getEmpAuth());
			pstmt.setInt(4, emp.getEmpSalary());
			pstmt.setString(5, emp.getEmpTel());
			pstmt.setString(6, emp.getEmpId());
			pstmt.setString(7, emp.getEmpPwd());
			pstmt.setInt(8, emp.getDept().getDeptNo());
			pstmt.setString(9, emp.getPic());
			pstmt.setString(10, emp.getEmpCode());

			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	
	@Override
	public int updateEmployeeNotPw(Employee emp) {
		String sql="update employee set empName=?,empTitle=?,empAuth=?,empSalary=?,empTel=?,empId=?, deptNo=?,pic=? where empCode=?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt= con.prepareStatement(sql)){
			
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getEmpTitle());
			pstmt.setString(3, emp.getEmpAuth());
			pstmt.setInt(4, emp.getEmpSalary());
			pstmt.setString(5, emp.getEmpTel());
			pstmt.setString(6, emp.getEmpId());
			pstmt.setInt(7, emp.getDept().getDeptNo());
			pstmt.setString(8, emp.getPic());
			pstmt.setString(9, emp.getEmpCode());

			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public int deleteEmployee(Employee emp) {
		String sql="delete from employee where empCode=?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt= con.prepareStatement(sql)){
			pstmt.setString(1, emp.getEmpCode());

			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Employee> selectEmployeeByAll() {
		String sql="select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
				"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
				"   order by empCode";
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery()){
			List<Employee> list = new ArrayList<Employee>();
			
			while(rs.next()) {
				list.add(getEmployee(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		String empCode = rs.getString("empCode");
		String empName = rs.getString("empName");
		String empTitle = rs.getString("empTitle");
		String empAuth = rs.getString("empAuth");
		int empSalary = rs.getInt("empSalary");
		String empTel = rs.getString("empTel");
		String empId = rs.getString("empId");
		String empPwd =rs.getString("empPwd");
		Department dept= new Department(rs.getInt("d.deptNo")); //이부분 확인해보기
	    dept.setDeptName(rs.getString("d.deptName"));
		return new Employee(empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, dept);

	}
	private Employee getEmployeePic(ResultSet rs) throws SQLException {
		String empCode = rs.getString("empCode");
		String empName = rs.getString("empName");
		String empTitle = rs.getString("empTitle");
		String empAuth = rs.getString("empAuth");
		int empSalary = rs.getInt("empSalary");
		String empTel = rs.getString("empTel");
		String empId = rs.getString("empId");
		String empPwd =rs.getString("empPwd");
		Department dept= new Department(rs.getInt("d.deptNo")); //이부분 확인해보기
	    dept.setDeptName(rs.getString("d.deptName"));
	    String pic = rs.getString("pic");
	    return new Employee(empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, dept, pic);
		
		
	}

	@Override
	public List<Department> selectDeptByAll() {
		String sql="select deptNo, deptName from department order by deptNo";
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery()){
			List<Department> list = new ArrayList<Department>();
			
			while(rs.next()) {
				list.add(getDepartment(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptNo");
		String deptName =rs.getString("deptName");
		return new Department(deptNo, deptName);
	}

	@Override
	public int updateEmployeeAuth(Employee emp) {  //여기서 emp는 네가지 정보만 가지고 있음 
		String sql="update employee set empName=?,empTitle=?,empAuth=? where empCode=?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt= con.prepareStatement(sql)){
			
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getEmpTitle());
			pstmt.setString(3, emp.getEmpAuth());
			pstmt.setString(4, emp.getEmpCode());

			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Employee> selectEmployeeByPerform() {
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode` where empRetire =0\r\n" + 
				"group by e.`empCode`order by bonus desc, perf desc";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			List<Employee> list = new ArrayList<Employee>();
			while(rs.next()) {
				list.add(getEmpPerform(rs));
				
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Employee getEmpPerform(ResultSet rs) throws SQLException {
		String empCode = rs.getString("e.empCode");
		String empName = rs.getString("e.empName");
		String empTitle = rs.getString("e.empTitle");
		int perf = rs.getInt("perf");
		int bonus = rs.getInt("bonus");
	//	String vip = rs.getString("vip");
		String pCode= rs.getString("pCode");
		String pName = rs.getString("pName");
		return new Employee(empCode, empName, empTitle, perf, bonus, pCode, pName);
	}

	@Override
	public Employee selectEmpByName(String empName) throws SQLException {
		       Employee emp = null;
				String sql = "select empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
						"   from employee e left join department d on e.deptNo = d.deptNo where empRetire =0\r\n" + 
						"   where empName=?";
				
				try (Connection con = DriverManager.getConnection(jdbcDriver);
						PreparedStatement pstmt = con.prepareStatement(sql)){
					
					pstmt.setString(1,empName);
					
					try(ResultSet rs = pstmt.executeQuery();){
						if(rs.next()) {
							//list.add(getEmployee(rs));
							
							return getEmployee(rs);
						}
						
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
	}

	@Override
	public Employee selectOneEmployeeByPerform(String empCode) throws SQLException {
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where e.empCode=? and empRetire =0 group by e.`empCode`";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, empCode);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			if(rs.next()) {
				//list.add(getEmpPerform(rs));
			   return getEmpPerform(rs);
				
			  }
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// -----------------------------------------------------------------------
	//통계 
	@Override
	public int selectCountAllEmployee() {
	   String sql="select count(empCode) from employee";

	   try(Connection con = DriverManager.getConnection(jdbcDriver);
		   PreparedStatement pstmt = con.prepareStatement(sql);){
		  
		   try(ResultSet rs = pstmt.executeQuery();){
			   
			   while(rs.next()) {
			      return rs.getInt("count(empCode)");

			   }
			  
		   }	   	   
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		// TODO Auto-generated method stub
		return 0;
	}

	//부서 번호를 매개변수로 넣고 부서별 인원 수 구하기
	@Override
	public int selectCountMemberByDept(int dept) {
		String sql= "select count(*) from employee e  where `deptNo` =?";
		
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, dept);
			
			try(ResultSet rs = pstmt.executeQuery();){
				while(rs.next()) {
					return rs.getInt("count(*)");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int selectCountMemberByTitle(String empTitle) {
		String sql="select  count(empCode) from employee\r\n" + 
				"where `empTitle` =?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, empTitle);
			
			try(ResultSet rs = pstmt.executeQuery();){
				while(rs.next()) {
					return rs.getInt("count(empCode)");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int selectAvgOfSalary() {
		String sql="select ceiling((sum(empSalary))/(count(*))) as 'avgSal' from employee where empRetire =0";
		
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				){
			
			while(rs.next()) {
				return rs.getInt("avgSal");
			}
				
		     
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int selectTotalSalary() {
		String sql="select sum(empSalary) as 'totalSal' \r\n" + 
				"   from employee e";
		
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				return rs.getInt("totalSal");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}

	
	// 부서로 검색하기 
	@Override
	public List<Employee> selectEmpByDept(String empItem, int empRetired) throws SQLException {
		 Employee emp = null;
		 List<Employee> list = new ArrayList<Employee>();
			String sql = "select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
					"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
					"   where d.deptName=? and empRetire =?";
			
			try (Connection con = DriverManager.getConnection(jdbcDriver);
					PreparedStatement pstmt = con.prepareStatement(sql)){
				
				pstmt.setString(1,empItem);
				pstmt.setInt(2, empRetired);
				
				try(ResultSet rs = pstmt.executeQuery();){
					while(rs.next()) {
						list.add(getEmployee(rs));
						
						
						//return getEmployee(rs);
					}
					return list;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	@Override
	public List<Employee> selectEmpByDeptNo(int empItem) throws SQLException {
		 Employee emp = null;
		 List<Employee> list = new ArrayList<Employee>();
			String sql = "select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
					"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
					"   where d.deptNo=?";
			
			try (Connection con = DriverManager.getConnection(jdbcDriver);
					PreparedStatement pstmt = con.prepareStatement(sql)){
				
				pstmt.setInt(1,empItem);
				
				try(ResultSet rs = pstmt.executeQuery();){
					while(rs.next()) {
						list.add(getEmployee(rs));
						
						
						//return getEmployee(rs);
					}
					return list;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public List<Employee> selectEmpByNo(String empItem) throws SQLException {
		Employee emp = null;
		 List<Employee> list = new ArrayList<Employee>();
			String sql = "select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
					"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
					"   where empCode=? and empRetire =0";
			
			try (Connection con = DriverManager.getConnection(jdbcDriver);
					PreparedStatement pstmt = con.prepareStatement(sql)){
				
				pstmt.setString(1,empItem);
				
				try(ResultSet rs = pstmt.executeQuery();){
					while(rs.next()) {
						list.add(getEmployee(rs));
						
						
						//return getEmployee(rs);
					}
					return list;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public List<Employee> selectEmpByTitle(String empItem, int empRetired) throws SQLException {
		Employee emp = null;
		 List<Employee> list = new ArrayList<Employee>();
			String sql = "select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
					"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
					"   where empTitle=? and empRetire =?";
			
			try (Connection con = DriverManager.getConnection(jdbcDriver);
					PreparedStatement pstmt = con.prepareStatement(sql)){
				
				pstmt.setString(1,empItem);
				pstmt.setInt(2, empRetired);
				
				try(ResultSet rs = pstmt.executeQuery();){
					while(rs.next()) {
						list.add(getEmployee(rs));
						
						
						//return getEmployee(rs);
					}
					return list;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}

	// 실적테이블 검색조건에 따라 구현되는 서비스 
	@Override
	public List<Employee> selectEmpByNameListForPerform(String empItem) throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where e.empName like ? and empRetire =0 group by e.`empCode`";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, "%"+empItem+"%");
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPerform(rs));
			   //return getEmpPerform(rs);
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmpByDeptForPerform(String empItem) throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, d.deptName, pl.`planDetail` as pCode, pl.`planName` as pName \r\n" + 
				"		from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join department d on e.`deptNo` = d.`deptNo` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"				where d.deptName =? and empRetire =0 group by e.`empCode`";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, empItem);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPerform(rs));
			   //return getEmpPerform(rs);
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmpByNoForPerform(String empItem) throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus,pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where e.empCode = ? and empRetire =0 group by e.`empCode`";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, empItem);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPerform(rs));
			   //return getEmpPerform(rs);
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmpByTitleForPerform(String empItem) throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where e.empTitle = ? and empRetire =0 group by e.`empCode`";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, empItem);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPerform(rs));
			   //return getEmpPerform(rs);
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Employee selectEmpByCode(String empCode, int empRetired) throws SQLException {
		Employee emp = null;
		String sql = "select empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo, pic from employee e left join department d on e.deptNo = d.deptNo where empCode=? and empRetire =?";
		
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1,empCode);
			pstmt.setInt(2, empRetired);
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					//list.add(getEmployee(rs));
					
					emp = getEmployeePic(rs);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	@Override
	public Employee selectEmpByCode(String empCode) throws SQLException {
		Employee emp = null;
		String sql = "select empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo, pic from employee e left join department d on e.deptNo = d.deptNo where empCode=?";
		
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1,empCode);
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					//list.add(getEmployee(rs));
					
					emp = getEmployeePic(rs);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	// 실적 랭킹
	
	
	@Override
	public List<Employee> selectRank() {
		String sql="select e.empCode, e.empName, e.empTitle, e.pic, r.perf, r.bonus  from employee e left join ranking r on e.`empCode` =r.empCode where e.`deptNo` =2 and empRetire =0 order by bonus desc, perf desc";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			List<Employee> list = new ArrayList<Employee>();
			while(rs.next()) {
				list.add(getEmpRanking(rs));
				
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Employee getEmpRanking(ResultSet rs) throws SQLException {
		String empCode = rs.getString("e.empCode");
		String empName = rs.getString("e.empName");
		String empTitle = rs.getString("e.empTitle");
		String pic = rs.getString("e.pic");
		int perf = rs.getInt("r.perf");
		int bonus = rs.getInt("r.bonus");

		return new Employee(empCode, empName, empTitle, pic, perf, bonus);
	}

	@Override
	public Employee selectEmpByEmpCode(String empCode) throws SQLException {
		Employee emp = null;
		String sql = "select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo, pic, empRetire\r\n" + 
				"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
				"   where empCode=? and empRetire =0";
		
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1,empCode);
			
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					//list.add(getEmployee(rs));
					
					return getEmployeeFull(rs);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Employee getEmployeeFull(ResultSet rs) throws SQLException {
		
			String empCode = rs.getString("empCode");
			String empName = rs.getString("empName");
			String empTitle = rs.getString("empTitle");
			String empAuth = rs.getString("empAuth");
			int empSalary = rs.getInt("empSalary");
			String empTel = rs.getString("empTel");
			String empId = rs.getString("empId");
			String empPwd =rs.getString("empPwd");
			Department dept= new Department(rs.getInt("d.deptNo")); //이부분 확인해보기
		    dept.setDeptName(rs.getString("d.deptName"));
		    String pic = rs.getString("pic");
		    boolean empRetire = (rs.getInt("empRetire")==0?false:true);
			return new Employee(empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, dept, pic, empRetire);

		
	}
	
	private Employee getEmployeeFull2(ResultSet rs) throws SQLException {
		
		String empCode = rs.getString("empCode");
		String empName = rs.getString("empName");
		String empTitle = rs.getString("empTitle");
		String empAuth = rs.getString("empAuth");
		int empSalary = rs.getInt("empSalary");
		String empTel = rs.getString("empTel");
		String empId = rs.getString("empId");
		String empPwd =rs.getString("empPwd");
		Department dept= new Department(rs.getInt("d.deptNo")); //이부분 확인해보기
	    dept.setDeptName(rs.getString("d.deptName"));
		return new Employee(empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, dept);

	
}

	@Override
	public Employee selectEmpByEmpId(String empId) throws SQLException {
		Employee emp = null;
		String sql = "select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
				"   from employee e left join department d on e.deptNo = d.deptNo \r\n" + 
				"   where empId=? and empRetire =0";
		
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1,empId);
			
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					//list.add(getEmployee(rs));
					
					return getEmployeeFull2(rs);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int changeEmpStatus(Employee emp) {
		String sql="update employee set empRetire =1 where empCode =?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt= con.prepareStatement(sql)){
			
			pstmt.setString(1, emp.getEmpCode());

			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Employee> selectRetiredList() throws SQLException {
		String sql="select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
				"   from employee e left join department d on e.deptNo = d.deptNo where empRetire =1\r\n" + 
				"   order by empCode";
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery()){
			List<Employee> list = new ArrayList<Employee>();
			
			while(rs.next()) {
				list.add(getEmployee(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public List<Employee> selectEmpPerformanceDetail(String empItem) throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName , c.custCode, c.custName, p1.planCode, p1.planName\r\n" + 
				"  from performance p left join plan p1 on p.planCode =p1.planCode left join employee e on p.empCode =e.empCode left join customer c on p.custCode =c.custCode where e.empCode=?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, empItem);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPersonalPerform(rs));
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Employee getEmpPersonalPerform(ResultSet rs) throws SQLException {
		String empCode = rs.getString("e.empCode");
		String empName = rs.getString("e.empName");
		Customer customer = new Customer(rs.getString("c.custCode"));
		customer.setCustName(rs.getString("c.custName"));
		Plan plan = new Plan(rs.getString("p1.planCode"));
		plan.setPlanName(rs.getString("p1.planName"));
		return new Employee(empCode, empName, customer, plan);
	}

	@Override
	public List<Employee> selectExistEmployee() {
		String sql="select  empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo\r\n" + 
				"   from employee e left join department d on e.deptNo = d.deptNo where empRetire =0\r\n" + 
				"   order by empCode";
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
						ResultSet rs = pstmt.executeQuery()){
			List<Employee> list = new ArrayList<Employee>();
			
			while(rs.next()) {
				list.add(getEmployee(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}


   //타겟상품 상품별로 실적 계산하기위한 구문
	@Override
	public List<Employee> selectEmployeeByPerformByTarget(String pCode) {
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode` \r\n" + 
				"where pl.planCode =?\r\n" + 
				"group by e.`empCode`order by bonus desc, perf desc";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, pCode);
			List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
				while(rs.next()) {
			
				list.add(getEmpPerform(rs));
    
		     	}
			  return list;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
	//페이징 위한 select List문들
	@Override
	public List<Employee> selectExistEmployeeLimit(SearchCriteria cri) {
		StringBuilder sqlBuilder = new StringBuilder("select empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, d.deptName, d.deptNo from employee e left join department d on e.deptNo = d.deptNo where empRetire =0");
		List<Employee> list = null;
		if(cri.getSearchType()!=null) {
			switch(cri.getSearchType()) {
			case "사원번호":
				sqlBuilder.append(" and empcode = ?");
				break;
			case "사원이름":
				sqlBuilder.append(" and empname = ?");
				break;
			case "부서":
				if(cri.getKeyword().equals("인사")) {
					sqlBuilder.append(" and deptname = ?");
				}
				else if(cri.getKeyword().equals("고객")) {
					sqlBuilder.append(" and deptname = ?");
				}
				break;
			case "직급":
				sqlBuilder.append(" and emptitle = ?");
				break;
			}
			sqlBuilder.append(" order by empcode limit ?, ?");
		}
		else {
			sqlBuilder.append(" order by empcode limit ?, ?");
		}
		String sql = sqlBuilder.toString();
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			if(cri.getSearchType()!=null) {
				if(cri.getSearchType().equals("")) {
					pstmt.setInt(1, cri.getPageStart());
					pstmt.setInt(2, cri.getPerPageNum());
				}
				else {
					pstmt.setString(1,cri.getKeyword());
					pstmt.setInt(2, cri.getPageStart());
					pstmt.setInt(3, cri.getPerPageNum());
				}	
			}
			else {
				pstmt.setInt(1, cri.getPageStart());
				pstmt.setInt(2, cri.getPerPageNum());
			}
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					list = new ArrayList<Employee>();
					do {
						list.add(getEmployee(rs));    
					}while(rs.next());
				}
				return list;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	
	@Override
	public List<Employee> selectEmployeeByPerformLimit(SearchCriteria cri) {
		StringBuilder sqlBuilder =new StringBuilder("select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"				from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode` where empRetire =0\r\n" + 
				"				 ");
		List<Employee> list = null;
		if(cri.getSearchType()!=null) {
			switch(cri.getSearchType()) {
			case "사원번호":
				sqlBuilder.append(" and e.empcode = ?");
				break;
			case "사원이름":
				sqlBuilder.append(" and e.empname = ?");
				break;
			case "직급":
				sqlBuilder.append(" and e.emptitle = ?");
				break;
			}
			sqlBuilder.append("group by e.`empCode` order by bonus desc, perf desc limit ?, ?");
		}
		else {
			sqlBuilder.append("group by e.`empCode` order by bonus desc, perf desc limit ?, ?");
		}
		String sql = sqlBuilder.toString();
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			if(cri.getSearchType()!=null) {
				if(cri.getSearchType().equals("")) {
					pstmt.setInt(1, cri.getPageStart());
					pstmt.setInt(2, cri.getPerPageNum());
				}
				else {
					pstmt.setString(1,cri.getKeyword());
					pstmt.setInt(2, cri.getPageStart());
					pstmt.setInt(3, cri.getPerPageNum());
				}	
			}
			else {
				pstmt.setInt(1, cri.getPageStart());
				pstmt.setInt(2, cri.getPerPageNum());
			}
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					list = new ArrayList<Employee>();
					do {
						list.add(getEmpPerform(rs));    
					}while(rs.next());
				}
				return list;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public Employee selectOneEmployeeByPerformLimit(String empCode, int startRow, int endRow) throws SQLException {
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where e.empCode=? and empRetire =0 group by e.`empCode` limit ?, ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, empCode);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
			   return getEmpPerform(rs);
			  }
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmpByNameListForPerformLimit(String empItem, int startRow, int endRow) throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where e.empName like ? and empRetire =0 group by e.`empCode` limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, "%"+empItem+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPerform(rs));
			   //return getEmpPerform(rs);
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmpByDeptForPerformLimit(String empItem, int startRow, int endRow) throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, d.deptName, pl.`planDetail` as pCode, pl.`planName` as pName \r\n" + 
				"		from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join department d on e.`deptNo` = d.`deptNo` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"				where d.deptName like ? and empRetire =0 group by e.`empCode` limit ?,? ";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, "%"+empItem+"%");       
			pstmt.setInt(2, startRow);      
			pstmt.setInt(3, endRow);        
			//List<Employee> list = new ArrayList<Employee>();   
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPerform(rs));
			   //return getEmpPerform(rs);
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmpByTitleForPerformLimit(String empItem, int startRow, int endRow)throws SQLException {
		List<Employee> list = new ArrayList<Employee>();
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where e.empTitle = ? and empRetire =0 group by e.`empCode` limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				){
			pstmt.setString(1, empItem);   
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			//List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
			
			while(rs.next()) {
				list.add(getEmpPerform(rs));
			   //return getEmpPerform(rs);
				
			  }
			 return list;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Employee> selectEmployeeByPerformByTarget(String pCode, int startRow, int endRow) {
		String sql="select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode`\r\n" + 
				"where p.planCode =?\r\n" + 
				"group by e.`empCode`order by bonus desc, perf desc limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, pCode);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			List<Employee> list = new ArrayList<Employee>();
			try(ResultSet rs = pstmt.executeQuery();){
				while(rs.next()) {
			
				list.add(getEmpPerform(rs));
    
		     	}
			  return list;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) {
		int count = 0;
		StringBuilder sqlBuilder = new StringBuilder("select count(empCode) from employee e left join department d on e.deptNo = d.deptNo where empRetire =0");
		if(cri.getSearchType()!=null) {
			switch(cri.getSearchType()) {
			case "사원번호":
				sqlBuilder.append(" and empcode = ?");
				break;
			case "사원이름":
				sqlBuilder.append(" and empname = ?");
				break;
			case "부서":
				if(cri.getKeyword().equals("인사")) {
					sqlBuilder.append(" and deptname = ?");
				}
				else if(cri.getKeyword().equals("고객")) {
					sqlBuilder.append(" and deptname = ?");
				}
				break;
			case "직급":
				sqlBuilder.append(" and emptitle = ?");
				break;
			}
		}
		String sql = sqlBuilder.toString();
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			if(cri.getSearchType()!=null) {
				if(!cri.getSearchType().equals("")) {
					pstmt.setString(1,cri.getKeyword());
				}	
			}
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					count = rs.getInt("count(empCode)");
				}
			}	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int totalSearchCountBonus(SearchCriteria cri) {
		int count = 0;
		StringBuilder sqlBuilder = new StringBuilder("select e.empCode, e.empName, e.empTitle, count(if(p.custCode=null,0,p.custCode)) as perf , if(count(if(p.custCode=null,0,p.custCode))>=10,e.`empSalary`*0.1,0) as bonus, pl.`planDetail` as pCode, pl.`planName` as pName\r\n" + 
				"				from employee e left join performance p on e.`empCode` = p.`empCode`  left join customer c on p.`custCode`=c.`custCode` left join plan pl on pl.`planCode` = p.`planCode` where empRetire =0\r\n" + 
				"				group by e.`empCode`order by bonus desc, perf desc ");
		if(cri.getSearchType()!=null) {
			switch(cri.getSearchType()) {
			case "사원번호":
				sqlBuilder.append(" and e.empcode = ?");
				break;
			case "사원이름":
				sqlBuilder.append(" and e.empname = ?");
				break;

			case "직급":
				sqlBuilder.append(" and e.emptitle = ?");
				break;
			}
		}
		String sql = sqlBuilder.toString();
		try (Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			if(cri.getSearchType()!=null) {
				if(!cri.getSearchType().equals("")) {
					pstmt.setString(1,cri.getKeyword());
				}	
			}
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					count = rs.getInt("count(e.empCode)");
				}
			}	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
