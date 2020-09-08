package pw.cdmi.open.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

/************************************************************
 * 专员信息实体
 * 
 * @author 佘朝军
 * @version iSoc Service Platform, 2015-5-6
 ************************************************************/
@NamedQueries({ @NamedQuery(name = "Commissioner.findAll", query = "from Commissioner c"),
        @NamedQuery(name = "Commissioner.findAllByCompanyId", query = "from Commissioner c where c.companyId=?"),
        @NamedQuery(name = "Commissioner.findByName", query = "from Commissioner c where c.name like %:name% and  c.companyId=:companyId") })
@Data
@Entity
@Table(name = "ept_commissioner")
public class Commissioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;					// 专员信息编号

    @Column(length = 50, nullable = false)
    private String name;			// 专员岗位名称

    private String description;		// 专员岗位描述

    private String companyId;

}
