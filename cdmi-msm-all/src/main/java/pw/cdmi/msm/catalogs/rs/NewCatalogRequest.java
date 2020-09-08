package pw.cdmi.msm.catalogs.rs;

import lombok.Data;

@Data
public class NewCatalogRequest {
	private String name;            // 目录的名称
	
	private String parent_id;		// 父目录的ID
	
	private String icon_image;		// 目录的ICON图标地址
	
	private String summary;			// 目录的摘要与说明
}
