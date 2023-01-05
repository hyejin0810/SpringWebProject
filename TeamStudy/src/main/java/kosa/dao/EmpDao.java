package kosa.dao;

import java.sql.SQLException;
import java.util.List;

import kosa.vo.Emp;

public interface EmpDao {

	// 전체 게시물
	List<Emp> getEmpAllList() throws ClassNotFoundException, SQLException;

	// 게시물 수
	int getEmpAllCount() throws ClassNotFoundException, SQLException;

	// 페이지 처리
	public List<Emp> getPagedEmpList(int currpage, int pagesize) throws ClassNotFoundException, SQLException;

	// 사원추가
	public int insertAccount(Emp emp) throws ClassNotFoundException, SQLException;

	// 비동기 사원추가
	public int insertEmp(Emp emp) throws ClassNotFoundException, SQLException;

	// id로 사원번호 검색
	public String IdIsExist(String id) throws ClassNotFoundException, SQLException;

	// 정보 수정
	public int updateEmp(Emp e) throws ClassNotFoundException, SQLException;

	// 사원 삭제
	public int deleteEmp(String empno) throws ClassNotFoundException, SQLException;

	// 사원번호로 사원정보 보기
	Emp getDetailEmp(String empno) throws ClassNotFoundException, SQLException;

	// 사원번호로 사원정보
	Emp getEmpByEmpno(String empno) throws ClassNotFoundException, SQLException;

	// 이름으로 사원정보 검색
	public List<Emp> searchMember(Emp emp) throws ClassNotFoundException, SQLException;

	// 검색
	public List<Emp> searchAjax(Emp emp) throws ClassNotFoundException, SQLException;

}
