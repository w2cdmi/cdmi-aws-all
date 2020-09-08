package pw.cdmi.msm.catalogs.rs;

import lombok.Data;

@Data
public class ListCatalogResponse {
	
	private String id;				// 目录的id
	private String name;            // 目录的名称
	private String icon_image;		// 目录的ICON图标地址
}
