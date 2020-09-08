package pw.cdmi.open.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pw.cdmi.open.Configuration;
import pw.cdmi.utils.model.MailMessage;

/****************************************************
 * 工具类，提供邮件发送功能。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 29, 2014
 ***************************************************/
public class JMailUtils {
	protected static Log log = LogFactory.getLog(JMailUtils.class);
	
	@Autowired
	private Configuration configuration;
	
	/** JMail功能的配置信息 **/
	private static Properties p = new Properties();
	static {
		initProperties("local.properties");
	}

	private JMailUtils() {
	}

	/**
	 * 把邮件主题转换为中文
	 * 
	 * @param strText
	 * @return
	 */
	private static String transferChinese(String strText) {
		try {
			//strText = MimeUtility.encodeText(new String(strText.getBytes(),"GB2312"), "GB2312", "B");
			strText = MimeUtility.encodeText(strText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strText;
	}

	public static boolean sendMail(String to, String from, MailMessage mail) {
		// 构造mail session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", p.getProperty("mail.smtp.host"));
		props.put("mail.smtp.auth", p.getProperty("mail.smtp.auth"));
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(p
								.getProperty("mail.smtp.username"), p
								.getProperty("mail.smtp.password"));
					}
				});
		try {
			// 构造MimeMessage 并设定基本的值
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			String subject = transferChinese(mail.getSubject());
			msg.setSubject(subject);
			// 构造Multipart
			Multipart mp = new MimeMultipart();
			// 向Multipart添加正文
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setText(mail.getContent());
			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbpContent);
			// 向Multipart添加附件
			Vector<String> files = mail.listAttachfiles();
			Enumeration<String> efile = files.elements();
			String filename = null;
			while (efile.hasMoreElements()) {
				MimeBodyPart mbpFile = new MimeBodyPart();
				filename = efile.nextElement().toString();
				FileDataSource fds = new FileDataSource(filename);
				mbpFile.setDataHandler(new DataHandler(fds));
				mbpFile.setFileName(fds.getName());
				// 向MimeMessage添加（Multipart代表附件）
				mp.addBodyPart(mbpFile);
			}
			files.removeAllElements();
			// 向Multipart添加MimeMessage
			msg.setContent(mp);
			msg.setSentDate(new Date());
			// 发送邮件
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			return false;
		}
		return true;
	}

	public static boolean sendMail(String to, MailMessage mail) {
		//return sendMail(to, p.getProperty("mail.from"), mail);
		return sendMail(to, p.getProperty("mail.smtp.username"), mail);
	}

	/**
	 * 类初始化加载JMail配置文件
	 * 
	 * @param propertyFileName
	 */
	private static void initProperties(String propertyFileName) {
		InputStream in = null;
		try {
			in = JMailUtils.class.getClassLoader().getResourceAsStream(
					propertyFileName);
			if (in != null)
				p.load(in);
		} catch (IOException e) {
			log.error(
					"未能加载到邮件功能的配置文件，该文件的名称为：'JMail.properties',应放到classes的根目录..",
					e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("'JMail.properties'文件关闭失败..", e);
				}
			}
		}
	}
}
