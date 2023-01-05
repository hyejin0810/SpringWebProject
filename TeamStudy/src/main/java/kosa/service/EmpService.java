package kosa.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kosa.dao.EmpDao;
import kosa.vo.Emp;

@Service
public class EmpService {

	private SqlSession sqlsession;

	@Autowired
	public void setSqlsession(SqlSession sqlsession) {
		this.sqlsession = sqlsession;
	}

	// 전체목록 조회
	public List<Emp> getEmpAllList() {
		List<Emp> list = null;
		try {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			list = empdao.getEmpAllList();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 조건조회
	public Emp getDetailEmp(String empno) {
		Emp emp = null;
		try {
			// 동기화/////////////////////////////////////////////////////
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			///////////////////////////////////////////////////////////
			emp = empdao.getDetailEmp(empno);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	// 이름으로 사원정보 검색
	public List<Emp> searchMember(Emp emp) {
		List<Emp> list = null;
		try {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			list = empdao.searchMember(emp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 삽입
	public int insertEmp(Emp emp) {

		int result = 0;

		try {

			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			result = empdao.insertEmp(emp);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 수정 서비스
	public Emp updateEmp(String empno) {
		Emp emp = null;
		try {
			EmpDao empdao = sqlsession.getMapper(EmpDao.class);
			emp = empdao.getDetailEmp(empno);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	// 수정하기 처리
	public String updateEmp(Emp emp) {
		EmpDao empdao = sqlsession.getMapper(EmpDao.class);
		try {
			empdao.updateEmp(emp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:empDetail.do?empno=" + emp.getEmpno();
	}

	// 삭제
	public String empDel(String empno) {
		EmpDao empdao = sqlsession.getMapper(EmpDao.class);
		try {
			empdao.deleteEmp(empno);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:emp.do";
	}

}
