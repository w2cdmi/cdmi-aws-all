package pw.cdmi.msm.question.model.entities;

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
 * 问题的分类信息列表
 * @author wsl
 * @version cdm Service Platform, 2016年6月21日
 ***********************************************************
 */
@Data
@Entity
@Table(name="p_question_class")
@Document(collection="p_question_class")
public class QuestionClass {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true,length=32)
    private String id;
    @Column(length=32)
    private String modelId;//    问题归属的模块类型编号 String(32)  N       
    @Column(length=90)
    private String name;//    分类名称    String(90)  Y   NN  
    @Column(length=512)
    private String description;// 对分类的简要描述    String(512) N   NN  
    @Column(length=32)
    private String parentId;//   父类型编号   String(32)  N       
    @Column(length=32)
    private String siteId;// 对应的应用编号 String(32)  Y   NN  
//    DataStatus  数据状态    枚举  Y   对应全局的DataStatus枚举   
}
