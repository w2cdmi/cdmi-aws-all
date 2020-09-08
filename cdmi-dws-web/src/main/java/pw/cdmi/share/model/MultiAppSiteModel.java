package pw.cdmi.share.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
public class MultiAppSiteModel {
	//数据所对应的site的ID，在微信第三方开放平台中，该ID对应的开发平添客户的ID
	private String site_id;
	//数据所对应的应用的ID
	private String app_id;
	//为对象创建的全局唯一ID
	
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
	private String id;
	
}
