package pw.cdmi.utils.model;

import java.util.Vector;

/****************************************************
 * 系统向用户发送的邮件消息类。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 29, 2014
 ***************************************************/
public class MailMessage {
	
	private String subject = "";// 邮件主题
	private String content = "";// 邮件正文
	private Vector<String> files = new Vector<String>();// 附件文件集合

	public MailMessage() {
	}

	public MailMessage(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject(){
		return this.subject;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	// 增加附件
	public void attachfile(String fname) {
		files.addElement(fname);
	}
	
	public Vector<String> listAttachfiles(){
		return this.files;
	}
}
