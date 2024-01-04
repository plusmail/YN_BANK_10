package com.yi.dao;

import java.sql.SQLException;
import java.util.List;

import com.yi.dto.Department;
import com.yi.dto.Employee;
import com.yi.handler.paging.SearchCriteria;

public interface EmployeeDao {
	abstract Employee getEmpIdPass(Employee emp) throws SQLException;
	abstract Employee getEmpAuth(Employee emp) throws SQLException;
	
	Employee selectEmpByEmpId(String empId)throws SQLException;
	Employee selectEmpByEmpCode(String empCode) throws SQLException;
    //이름으로 검색
	List<Employee> selectEmpByNameList(String empName, int empRetired)throws SQLException;
	Employee selectEmpByName(String empName)throws SQLException;
	Employee selectEmpByCode(String empCode) throws SQLException;
	Employee selectEmpByCode(String empCode, int empRetired)throws SQLException;
	//부서로 검색
	List<Employee> selectEmpByDept(String empItem, int empRetired)throws SQLException;
	List<Employee> selectEmpByDeptNo(int empItem)throws SQLException;
	List<Employee> selectEmpByNo(String empItem)throws SQLException;
	List<Employee> selectEmpByTitle(String empItem, int empRetired)throws SQLException;
	
	//퍼포먼스 부분 리스트 조인테이블 불러오기
	List<Employee> selectEmpByNameListForPerform(String empItem)throws SQLException;
	
	//퍼포먼스 부분 리스트 조인테이블 불러오기 페이징
	List<Employee> selectEmpByNameListForPerformLimit(String empItem, int startRow, int endRow)throws SQLException;
	
	List<Employee> selectEmpByDeptForPerform(String empItem)throws SQLException;
	List<Employee> selectEmpByDeptForPerformLimit(String empItem, int startRow, int endRow)throws SQLException;
	
	List<Employee> selectEmpByNoForPerform(String empItem)throws SQLException;
	
	List<Employee> selectEmpByTitleForPerform(String empItem)throws SQLException;
	//전체 실적 조회 페이징 타이틀로 검색
	List<Employee> selectEmpByTitleForPerformLimit(String empItem, int startRow, int endRow)throws SQLException;
	
	//디테일한 실적 내역 리스트로 부르기 
	List<Employee> selectEmpPerformanceDetail(String empItem)throws SQLException;
	
    //전체 사원 리스트
	List<Employee> selectEmployeeByAll();
	//근무직원만
	List<Employee> selectExistEmployee();
	
	
	//페이징을 위한 목록 (전체)
	List<Employee> selectExistEmployeeLimit(SearchCriteria cri);
	int totalSearchCount(SearchCriteria cri);
	
	
	//실적관련 사원 리스트
	List<Employee> selectEmployeeByPerform();
	List<Employee> selectEmployeeByPerformByTarget(String pCode);
	List<Employee> selectEmployeeByPerformByTarget(String pCode , int startRow, int endRow );
	
	//실적관련 사원 페이징 리스트
	List<Employee> selectEmployeeByPerformLimit(SearchCriteria cri);
	int totalSearchCountBonus(SearchCriteria cri);
	
	//그중 한명의 실적만
	Employee selectOneEmployeeByPerform(String empCode) throws SQLException; 
	
	//그중 한명의 실적만 페이징
	Employee selectOneEmployeeByPerformLimit(String empCode, int startRow, int endRow) throws SQLException; 
	
	//실적 랭킹
	List<Employee> selectRank();
	
	//다이얼로그에 달 부서 리스트
    List<Department> selectDeptByAll();
    
	int insertEmployee(Employee emp);
	int updateEmployee(Employee emp);
	//비밀번호 빼고 업데이트
	int updateEmployeeNotPw(Employee emp);
	int deleteEmployee(Employee emp);
	//사원 퇴사 처리하기
	int changeEmpStatus(Employee emp);
	int updateEmployeeAuth(Employee emp);
	List<Employee> selectRetiredList() throws SQLException;
	
	
	//통계 
	// 전체 직원 수 구하기
	int selectCountAllEmployee();
	
	int selectCountMemberByDept(int dept);
	
	//타이틀별 직원 수 구하기
	int selectCountMemberByTitle(String empTitle);
	//1인 평균 월급 구하기
	int selectAvgOfSalary();
	int selectTotalSalary();
	
}
