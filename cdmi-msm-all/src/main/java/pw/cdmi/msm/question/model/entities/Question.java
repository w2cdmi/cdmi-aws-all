package pw.cdmi.msm.question.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pw.cdmi.msm.question.model.QuestionStatus;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * **********************************************************
 * <br/>
 * 用户问题表
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月27日
 ***********************************************************
 */

@Data
@Entity
@Table(name = "p_question")
@Document(collection = "p_question")
public class Question {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, length = 32)
    private String id;    // 编号

    @Column(length = 32)
    private String modelId;        // 问题归属的模块类型编号

    @Column(length = 32)
    private String classId;    // 问题归属的分类信息编号 String(32) N

    @Column(length = 512)
    private String title;// 问题标题 String(512) Y NN

    private String content;// 问题的内容 String N

    private Integer readNumber;// 该问题被查看阅读的次数 Integer N
    // private JSONArray Labels;// 为该问题设置的分类标签 JSONArray N

    private Boolean isCryptonym;// 是否匿名提问 Boolean N

    @Column(length = 90)
    private String cryptonymName;// 匿名时候可以使用任意名称 String(90) N

    @Column(length = 256)
    private String contactInfo;// 提问后的联系方式 String（256） N 该字段允许回复人看到问题后联系提问人

    private Date createDate; // 创建提问的时间 Time N

    private Date updateDate;// 最新一次更新时间 Time N

    @Column(length = 32)
    private String siteUserId;// 该问题的提出人 String(32) Y NN

    @Column(length = 32)
    private String receiverType;// 问题的接受人类型 String(32) N 关联Receiver

    @Column(length = 32)
    private String receiverId;// 问题的接受人 String（32） N 内容和ReceiverType相关联

    @Enumerated(EnumType.STRING)
    private QuestionStatus status;// 该问题的信息状态 枚举 N 对应QuesionStatus枚举，默认为New

    private Boolean isTop;// 该问题是否置顶显示 Boolean N

    private Integer orderNumber;// 对问题进行排序 Number N

    private String refObject;   // 该问题关联的对象 String N 问题关联xxx对象类型，开发者可自行定义

    private String refObjectId;// 问题关联的对象编号

    @Column(length = 32)
    private String siteId;// 对应的应用编号 String(32) Y NN
    // @Enumerated(EnumType.STRING)
    // private DataStatus DataStatus;
}
