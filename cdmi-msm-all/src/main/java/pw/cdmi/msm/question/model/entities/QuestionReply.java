package pw.cdmi.msm.question.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * **********************************************************
 * 问题回复信息表
 * @author wsl
 * @version cdm Service Platform, 2016年6月21日
 ***********************************************************
 */
@Data
@Entity
@Table(name = "p_question_reply")
@Document(collection = "p_question_reply")
public class QuestionReply {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true, length = 32)
    private String id;// 信息编号 String(32) Y PK NN

    @Column(length = 32)
    private String questionId;// 问题编号 String(32) N

    private String content;// 回复内容 String N

    private Integer readNumber;// 该回复被查看阅读的次数 Integer N

    private Boolean isCryptonym;// 是否匿名回复 Boolean N

    @Column(length = 90)
    private String cryptonymName;// 匿名时候可以使用任意名称 String(90) N

    @Column(length = 256)
    private String contactInfo;// 回复后的联系方式 String（256） N 该字段允许提问人看到问题的回复信息后后联系回复人

    private Date createDate;// 回复的时间 Time N

    private Date updateDate;// 最新一次更新时间 Time N

    private Integer agreeNumber;// 支持该回复信息的数量 Integer N 比如送花表示对结果进行赞同

    private Integer disagreeNumber;// 不同意该回复信息的数量 Integer N 比如送大便表示对结果不赞同

    @Column(length = 32)
    private String siteUserId;// 该回复人的帐号编号 String(32) N NN

    private Boolean isAccepted;// 该回复被提问人所接受 Boolean N

    @Column(length = 32)
    private String siteId;// 对应的应用编号 String(32) Y NN
    // DataStatus 数据状态 枚举 Y 对应全局的DataStatus枚举
}
