package pw.cdmi.msm.catalogs.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.core.entities.MultiAppSiteTenancyUserModel;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "msm_catalogs")
@Document(collection = "msm_catalogs")
public class Catalog extends MultiAppSiteTenancyUserModel{
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
    private String id; 				// 目录的ID
	
	
	private String name;            // 目录的名称
	
	@Column(name="parent_id", nullable = false)
	private String parentId;		// 父目录的ID
	
	@Column(name="icon_image", nullable = false)
	private String iconImage;		// 目录的ICON图标地址
	
	private String summary;			// 目录的摘要与说明
	
	private float sortValue;			// 排序值
}
