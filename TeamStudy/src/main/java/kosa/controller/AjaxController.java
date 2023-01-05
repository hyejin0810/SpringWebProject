package kosa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kosa.service.EmpService;
import kosa.vo.Emp;

@RestController // @Controller + 함수(@ResponseBody)
public class AjaxController {
	
	private EmpService empservice;

	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}
	
	//사원 전체 조회
	@RequestMapping("empList.do")
	public List<Emp> empList() {

		List<Emp> list = empservice.getEmpAllList();
		
		return list;
	}

	//사원 등록 비동기
	@RequestMapping("empWrite.do")
	public String empWrite(@RequestBody Emp emp) {
		
		String str = "";
		
		int result = empservice.insertEmp(emp);
		
		if(result > 0) {
			str = "suceess";
		}else {
			str = "false";
		}
		
		System.out.println(result);
		
		return str;
	}
	
	//사원번호 검색 비동기
	@RequestMapping("empSearch.do")
	public Emp empSearch(String empno) {
		
		Emp myemp = empservice.getDetailEmp(empno);
		
		return myemp;
	}
	
	//사원이름으로 조회 비동기
	@RequestMapping("enameSearch.do")
	public List<Emp> enameSearch(@RequestBody Emp emp){
		
		List<Emp> list = empservice.searchMember(emp);
		
		return list;
	}
	
	
}
