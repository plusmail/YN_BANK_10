package com.yi.service;

import java.sql.SQLException;
import java.util.List;

import com.yi.dao.EmployeeDao;
import com.yi.dao.impl.EmployeeDaoImpl;
import com.yi.dto.Department;
import com.yi.dto.Employee;
import com.yi.handler.paging.SearchCriteria;

public class EmployeeUIService {
    private  EmployeeDao empDao;
    
    
    public EmployeeUIService() {
    	empDao = EmployeeDaoImpl.getInstance();
    }
    
    
    public List<Employee> showEmpList(){
    	return empDao.selectEmployeeByAll();
    }
    //퇴사사원 빼고 조회 auth위함
    public List<Employee> showExistEmpList(){
    	return empDao.selectExistEmployee();
    }

    public Employee showPickedEmp2(String empCode) throws SQLException{
    	return empDao.selectEmpByEmpCode(empCode);
    }
    
    //아이디 중복 확인
    public Employee forCheckId(String empId) throws SQLException{
    	return empDao.selectEmpByEmpId(empId);
    }
    
    //사원 이름으로 검색
    public List<Employee> showPickedEmpList(String empName, int empRetired) throws SQLException{
    	return empDao.selectEmpByNameList(empName, empRetired);
    }
    public Employee showPickedEmp(String empName) throws SQLException{
    	return empDao.selectEmpByName(empName);
    }
    
    public Employee showPikedEmpByCode(String empCode) throws SQLException{
    	return empDao.selectEmpByCode(empCode);
    }
    
    
    public Employee showPikedEmpByCode(String empCode ,int empRetired) throws SQLException{
    	return empDao.selectEmpByCode(empCode, empRetired);
    }
    
    //근무하는 사원의 수만 
    public List<Employee> showPickedEmpByDept(String empItem, int empRetired) throws SQLException{
    	return empDao.selectEmpByDept(empItem, empRetired);
    }
    
    public List<Employee> showPickedEmpByDeptNo(int empItem) throws SQLException{
    	return empDao.selectEmpByDeptNo(empItem);
    }

 
    
    public List<Employee> showPickedEmpByEmpNo(String empItem) throws SQLException{
    	return empDao.selectEmpByNo(empItem);
    }
    public List<Employee> showPickedEmpByTitle(String empItem, int empRetired) throws SQLException{
    	return empDao.selectEmpByTitle(empItem,empRetired);
    }
    
    
    
    //보너스 
    public Employee showEmpPerformPicked(String empCode) throws SQLException {
    	return empDao.selectOneEmployeeByPerform(empCode);
    }
    
    //보너스 페이징 
    public Employee showEmpPerformPickedLimit(String empCode, int startRow, int endRow) throws SQLException {
    	return empDao.selectOneEmployeeByPerformLimit(empCode, startRow, endRow);
    }
    
    //퍼포먼스 테이블 리스트 불러오기
    
    public List<Employee> showPickedEmpListForPerform(String empItem) throws SQLException{
    	return empDao.selectEmpByNameListForPerform(empItem);
    }
    
    //퍼포먼스 테이블 리스트 불러오기 페이징 (이름으로 검색)
    
    public List<Employee> showPickedEmpListForPerformLimit(String empItem, int startRow, int endRow) throws SQLException{
    	return empDao.selectEmpByNameListForPerformLimit(empItem, startRow, endRow);
    }
    
    public List<Employee> showPickedEmpByDeptForPerform(String empItem) throws SQLException{
    	return empDao.selectEmpByDeptForPerform(empItem);
    }
    //퍼포먼스 테이블 리스트 불러오기 페이징 (부서로 검색)
    public List<Employee> showPickedEmpByDeptForPerformLimit(String empItem, int startRow, int endRow) throws SQLException{
    	return empDao.selectEmpByDeptForPerformLimit(empItem, startRow, endRow);
    }
    
    
    public List<Employee> showPickedEmpByEmpNoForPerform(String empItem) throws SQLException{
    	return empDao.selectEmpByNoForPerform(empItem);
    }
    public List<Employee> showPickedEmpByTitleForPerform(String empItem) throws SQLException{
    	return empDao.selectEmpByTitleForPerform(empItem);
    }
    //퍼포먼스 테이블 리스트 불러오기 페이징 (직급으로 검색)
    public List<Employee> showPickedEmpByTitleForPerformLimit(String empItem, int startRow, int endRow) throws SQLException{
    	return empDao.selectEmpByTitleForPerformLimit(empItem, startRow, endRow);
    }
    
    //퍼포먼스 디테일페이지에서 해당 사원의 실적 상세 정보를 불러오기 위함
    public List<Employee> showDetailEmpPerformance(String empItem) throws SQLException{
    	return empDao.selectEmpPerformanceDetail(empItem);
    }
    
    //사원 랭킹 리스트 부르기
    public List<Employee> showRank(){
    	return empDao.selectRank();
    }
    
    
    
    
    
    public List<Department> showDeptList(){
    	return empDao.selectDeptByAll();
    }
    
    
    public void removeEmp(Employee emp) {
    	empDao.deleteEmployee(emp);
    }
    //퇴사사원 처리 하기
    public void changeStatus(Employee emp) {
    	empDao.changeEmpStatus(emp);
    }
    //퇴사사원 리스트
    public List<Employee> showRetiredEmpList() throws SQLException{
    	return empDao.selectRetiredList();
    }
    
    //insert하는 부분 
    public void addEmp(Employee emp) {
    	empDao.insertEmployee(emp);
    }
    
    public void modifyEmp(Employee emp) {
    	empDao.updateEmployee(emp);
    }
    
    //비밀번호 제외하고 업데이트
    public void modifyEmpExceptForPwd(Employee emp) {
    	empDao.updateEmployeeNotPw(emp);
    }    
    
    public void modifyEmpAuth(Employee emp) {
    	empDao.updateEmployeeAuth(emp);
    }
    
    //사원 실적까지 포함
    public List<Employee> showEmpPerformance(){
    	return empDao.selectEmployeeByPerform();
    }
    
 
    //타겟 상품으로 실적 조회
    public List<Employee> showEmpPerformanceByTarget(String pCode){
    	return empDao.selectEmployeeByPerformByTarget(pCode);
    }
    
    //전체 사원 수 구하기
    public int countAllEmpNum() {
    	return empDao.selectCountAllEmployee();
    	
    }
    //부서별 사원 수 구하기
    // 인사부 - 1번  고객팀 2번
    public int countMemberByDepartment(int deptNo) {
    	return empDao.selectCountMemberByDept(deptNo);
    }
    
    //타이틀마다 사원 수 구하기{  
    public int countMemberByTitle(String empTitle) {
    	return empDao.selectCountMemberByTitle(empTitle);
    }
    
    //1인 평균 월급
    public int avgOfSalary() {
    	return empDao.selectAvgOfSalary();   	
    }
    //전체 월급 액수 
    public int totalSalary() {
    	return empDao.selectTotalSalary();
    }
    
    
 //페이징 처리
    
    //페이징을 위한 목록(전체)
    public List<Employee> showExistEmployeeLimit(SearchCriteria cri){
    	return empDao.selectExistEmployeeLimit(cri);
    }
	public int getTotalSearchCount(SearchCriteria cri) {
		return empDao.totalSearchCount(cri);
	}

	 //페이징 목록 (사원 실적까지 포함 limit)
    public List<Employee> showEmployeeByPerformLimit(SearchCriteria cri){
    	return empDao.selectEmployeeByPerformLimit(cri);
    }
    
    public List<Employee> showEmpPerformanceByTarget(String pCode, int startRow, int endRow){
    	return empDao.selectEmployeeByPerformByTarget(pCode, startRow, endRow);
    }
    
}
