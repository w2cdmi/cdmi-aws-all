package pw.cdmi.msm.question.rs.v1;

import java.util.Date;

import lombok.Data;

@Data
public class ShowQuestion {
    private String id;//问题编号
    
    private String classId;//问题类型编号
    
    private String className;//问题类型名称
    
    private String modelId;//问题咨询模块编号
    
    private String modelName;//问题咨询模块名称
    
    private String title;//问题标题
    
    private String content;//问题内容
    
    private Integer readNumber;//浏览次数
    
    private Boolean isCryptonym;// 是否匿名提问 Boolean N

    private String cryptonymName;// 匿名时候可以使用任意名称 String(90) N

    private String contactInfo;// 提问后的联系方式 String（256） N 该字段允许回复人看到问题后联系提问人

    private Date createDate; // 创建提问的时间 Time N

    private Date updateDate;// 最新一次更新时间 Time N

    private String siteUserId;// 该问题的提出人 String(32) Y NN
    
    private String siteUserName;//问题提出人的姓名

    private String receiverType;// 问题的接受人类型 String(32) N 关联Receiver

    private String receiverId;// 问题的接受人 String（32） N 内容和ReceiverType相关联

    private String status;// 该问题的信息状态 枚举 N 对应QuesionStatus枚举，默认为New

    private Boolean isTop;// 该问题是否置顶显示 Boolean N

    private Integer orderNumber;// 对问题进行排序 Number N

    private String refObject;   // 该问题关联的对象 String N 问题关联xxx对象类型，开发者可自行定义

    private String refObjectId;// 问题关联的对象编号

    private String siteId;// 对应的应用编号 String(32) Y NN
    
    private String siteName;//对应应用名称
    
    private String version; //版本号
}
