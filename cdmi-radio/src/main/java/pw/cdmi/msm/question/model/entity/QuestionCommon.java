package pw.cdmi.msm.question.model.entity;

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
 * <br/>
 * 常见问题信息表
 * @author wsl
 * @version cdm Service Platform, 2016年6月21日
 ***********************************************************
 */
@Data
@Entity
@Table(name="p_question_common")
@Document(collection="p_question_common")
public class QuestionCommon {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true,length=32)
    private String id;
    @Column(length=32)
    private String modelId;//    问题归属的模块类型编号 String(32)  N       
    @Column(length=32)
    private String classId;//    问题归属的分类信息编号 String(32)  N       
    @Column(length=512,unique=true)
    private String title;//   问题标题    String(512) Y   NN  
    private String content;// 问题的内容   String  N       
    private String answerContent;//   问题的标准答案 String  N       
    private Integer readNumber;//  该问题被查看阅读的次数 Integer N       
//    Labels  为该问题设置的分类标签 JSONArray   N 
    @Column(unique = true)
    private Integer orderNumber;// 对问题进行排序 Number  N       
    private String refObject;//   该问题关联的对象    String  N   问题关联xxx对象类型，开发者可自行定义    
    private String refObjectId;//    该问题关联的对象编号  String  N   问题关联的对象编号   
    private Date createDate;//  创建提问的时间 Time    N       
    private Date updateDate;//  最新一次更新时间    Time    N       
    @Column(length=32)
    private String siteId;// 对应的应用编号 String(32)  Y   NN  
//    DataStatus  数据状态    枚举  Y   对应全局的DataStatus枚举   
    

}
