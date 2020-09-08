package pw.cdmi.open.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 该类保存了系统采集到的原始邮件地址信息
 * @author 伍伟
 *
 */
@Data
public class EmailUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;								// 信息编号
	
	private String email;			//邮箱密码
	
	private String password;		//邮箱的密码
	
	private String name;			//对应人的称呼
	
	private String peopleId;		//对应人的信息编号
		
	private String userId;			//对应系统内帐号信息编号
}
