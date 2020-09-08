package pw.cdmi.open.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.open.model.Sex;
import pw.cdmi.open.model.UserStatus;

/****************************************************
 * 基础数据，居民身份信息。
 * 
 * @author 伍伟
 * @version CDMI Service Platform, July 23, 2014
 ***************************************************/
@Data
@Entity
@Table(name = "p_account")
// @NamedQueries({
// @NamedQuery(name = "UserAccount.deleteByPeopleId", query = "delete from UserAccount where peopleId = ?1 "),
// @NamedQuery(name = "UserAccount.findByUserNameAndPassword", query = "select ua from UserAccount ua where userName =
// ?1 and password = ?2 "),
// @NamedQuery(name = "UserAccount.findByPeople", query = "select ua from UserAccount ua where peopleId = ?1 ")})
public class UserAccount {

    @Id
    @Column(unique = true)
    private String id;						// 帐号信息编号

    @Column(nullable = false, unique = true)
    private String openId;					// 账号的OpenId

    private String peopleId;				// 该账号对应的真实信息Id

    @Column(unique = true)
    private String userName;				// 用户帐号名称

    @Column(unique = true)
    private Sex sex;						// 用户帐号性别
    
    private String isReal;			    	// 是否实名
    
    @Column(unique = true)
    private String mobile;					// 用户账号的手机号码

    @Column(unique = true)
    private String email;					// 用户账号的的邮件地址

    private String password;				// 账号密码

    private String nickName;				// 账号昵称

    private String headImage;				// 会员头像的URL

    @Enumerated(EnumType.STRING)
    private UserStatus status; 				// 账号状态

    private Date registerTime;				// 账号创建时间

    @Column(name = "protect_AQ")
    private String protectAQ;				// 用户在该系统中的密码提示问题，用JSON格式，Map方式保存

    /******** 账号上次登录时候的信息 *********/
    private Date lastLoginTime;				// 最后一次登录的时间

    private String latitude;				// 最后一次登录的地理位置纬度

    private String Longitude;				// 最后一次登录的地理位置经度

    private String accurate;				// 地理位置精度

    private String gisType;			    	// 坐标的类型，对应坐标类型枚举

    private String location;				// 上次访问的所在地址

    private String cityName;				// 上次登录时候所在城市

    private String language;				// 账号上次访问时选择的语言

    private String ipAddress;				// 账号上次访问时的IP地址

    private String validateCode;			// 激活码

}
