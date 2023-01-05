package kosa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kosa.service.EmpService;
import kosa.vo.Emp;

@Controller
@RequestMapping("/emp/")
public class EmpController {

	private EmpService empservice;

	@Autowired
	public void setEmpservice(EmpService empservice) {
		this.empservice = empservice;
	}

	// 전체조회
	@RequestMapping("empList.do")
	public String empList(Model model) {

		List<Emp> list = empservice.getEmpAllList();
		model.addAttribute("list", list);

		return "emp/empList";

	}

	// 상세보기
	@RequestMapping("empDetail.do")
	public String empDetail(String empno, Model model) {

		Emp emp = empservice.getDetailEmp(empno);
		model.addAttribute("emp", emp);
		return "emp/empDetail";
	}

	// 수정하기
	@GetMapping(value = "empEdit.do")
	public String empEdit(String empno, Model model) {
		Emp emp = null;
		try {
			emp = empservice.updateEmp(empno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("emp", emp);
		return "emp/empEdit";
	}

	// 수정 처리
	@PostMapping("empEdit.do")
	public String empEdit(Emp emp) {
		return empservice.updateEmp(emp);
	}

	// 삭제 하기
	@RequestMapping("empDel.do")
	public String empDel(String empno) {

		return empservice.empDel(empno);
	}
	
	@RequestMapping("chat.do")
	public String pollingView() {
		
		return "websocket/chat";
	}

}
