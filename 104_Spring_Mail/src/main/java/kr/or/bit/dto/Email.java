package kr.or.bit.dto;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Component
public class Email {
	private String title; // 제목
	private String content; // 내용
	private String tomail; // 받는 사람 이메일
	private String toname; // 받는 사람 이름
	private List<CommonsMultipartFile> files;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTomail() {
		return tomail;
	}
	public void setTomail(String tomail) {
		this.tomail = tomail;
	}
	public List<CommonsMultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<CommonsMultipartFile> files) {
		this.files = files;
	}
	public String getToname() {
		return toname;
	}
	public void setToname(String toname) {
		this.toname = toname;
	}
	@Override
	public String toString() {
		return "Email [title=" + title + ", content=" + content + ", tomail=" + tomail + ", toname=" + toname
				+ ", files=" + files + "]";
	}

}
