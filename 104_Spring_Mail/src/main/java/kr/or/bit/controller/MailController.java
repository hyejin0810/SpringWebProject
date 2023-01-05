package kr.or.bit.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.GenericServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import kr.or.bit.dto.Email;

@Controller
@RequestMapping("/mail/")
public class MailController {
	
	@Autowired
	private MailSender mailSender;
	
	
	
	@Autowired
	private SimpleMailMessage templateMailMessage;
	
	@RequestMapping(value="simpleMailSend.do", method=RequestMethod.GET)
	public String simpleMailSend() {
		
		return "simpleMailMessage";
	}
	
	@RequestMapping(value="simpleMailSend.do", method=RequestMethod.POST)
	public String simpleMailSend(Email email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject(email.getTitle());
		message.setFrom("bitcamp104@gmail.com");
		message.setText(email.getContent());
		message.setTo(email.getTomail());
		
		try {
			mailSender.send(message);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/main.do";
	}
	
	@RequestMapping(value="simpleMailMessage_Template.do", method=RequestMethod.GET)
	public String simpleMailMessageTemplate() {
		
		return "simpleMailMessage_Template";
	}
	
	@RequestMapping(value="simpleMailMessage_Template.do", method=RequestMethod.POST)
	public String simpleMailMessageTemplate(Email email) {
		SimpleMailMessage message = new SimpleMailMessage(templateMailMessage);
		message.setTo(email.getTomail());
		
		try {
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/main.do";
	}
	
	@RequestMapping(value="mimeMessage.do", method=RequestMethod.GET)
	public String mimeMessage() {
		
		return "mimeMessage";
	}
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@RequestMapping(value="mimeMessage.do", method=RequestMethod.POST)
	public String mimeMessage(Email email) {
		MimeMessage message = javaMailSender.createMimeMessage(); //JavaMailSender 인터페이스에서 
																  //createMimeMessage()를 이용해서 주소 받기
		try {
			message.setSubject(email.getTitle(), "utf-8");
			message.setText(email.getContent(), "utf-8", "html");
			message.setFrom(new InternetAddress("bitcamp104@gmail.com")); //parameter type이 Address임
			message.addRecipient(RecipientType.TO, new InternetAddress(email.getTomail()));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			javaMailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/main.do";
	}
	
	@RequestMapping(value="mimeMessageHelper.do", method=RequestMethod.GET)
	public String mimeMessageHelper() {
		return "mimeMessageHelper";
	}
	
	@RequestMapping(value="mimeMessageHelper.do", method=RequestMethod.POST)
	public String mimeMessageHelper(HttpServletRequest request, Email email) {
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
}
