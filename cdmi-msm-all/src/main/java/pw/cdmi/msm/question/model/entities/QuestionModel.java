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
 *问题的模块类型信息 
 * @author wsl
 * @version cdm Service Platform, 2016年6月21日
 ***********************************************************
 */
@Data
@Entity
@Table(name="p_question_model")
@Document(collection="p_question_model")
public class QuestionModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(unique = true,length=32)
    private String id;
    @Column(length=90,nullable=false)
    private String name;    //类型名称    String(90)  Y   NN  
    @Column(length=32,nullable=false)
    private String siteId;// 对应的应用编号 String(32)  Y   NN
    

}
