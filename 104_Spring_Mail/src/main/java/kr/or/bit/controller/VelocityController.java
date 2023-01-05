package kr.or.bit.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import kr.or.bit.dto.Email;

@Controller
@RequestMapping("/vm/")
public class VelocityController {

	@Autowired
	private JavaMailSender javaMailSender;
	
	
	
	@Autowired
	private View jsonview;
	
	@RequestMapping(value="velocity.do", method=RequestMethod.GET)
	public String velocity() {
		return "vm/velocity";
	}
	
	@RequestMapping(value="velocity.do", method=RequestMethod.POST)
	public String velocity(Email email, HttpServletRequest request) {
		
		List<CommonsMultipartFile> files = email.getFiles();
		List<String> filenames = new ArrayList<String>(); //파일명 담아넣기 (DB Insert)
		
		if(files != null && files.size() > 0) {
			   for(CommonsMultipartFile multifile : files) {
				    String filename = multifile.getOriginalFilename();
				    String path = request.getServletContext().getRealPath("/upload");
					String fpath = path + "\\" + filename;
					if(!filename.equals("")) { //파일 쓰기
						FileOutputStream fs = null;
						try {
							fs = new FileOutputStream(fpath);
							fs.write(multifile.getBytes());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							try {fs.close();} 
							catch (IOException e) {e.printStackTrace();}
						}
					}
					filenames.add(filename); //DB insert 파일명	
			   }
		}
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
		String baseUrl = request.getServletContext().getRealPath("/");
		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8"); //true로 해야 첨부파일 추가 가능
			messageHelper.setSubject(email.getTitle());
			messageHelper.setText(email.getContent(), true);
			
			messageHelper.setFrom("bitcamp104@gmail.com", "운영자");
			messageHelper.setTo(new InternetAddress(email.getTomail()));
			
			DataSource dataSource = new FileDataSource(baseUrl + "/upload/" + filenames.get(0));
			
			messageHelper.addAttachment(MimeUtility.encodeText(filenames.get(0), "utf-8", "B"), dataSource);
			javaMailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/main.do";
	}
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@RequestMapping(value="template.do", method=RequestMethod.POST)
	public View template(String vm, String toname, Model model) {
		if(vm.equals("default")) {
			model.addAttribute("content", "");
			return jsonview;
		}else {
			String templateLocation = vm + ".vm";
			
			Map<String, Object> vmmodel = new HashMap<>();
			vmmodel.put("toname", toname);
			
			String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
															templateLocation, "UTF-8", vmmodel);
			model.addAttribute("content", content);
			
			return jsonview;
		}
	}
	
}
