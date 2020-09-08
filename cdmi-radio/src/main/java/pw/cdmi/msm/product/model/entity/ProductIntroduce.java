package pw.cdmi.msm.product.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.radio.model.MultiAppSiteModel;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductIntroduce extends MultiAppSiteModel{
	private String product_id;		//对应的产品编号
	private String title;			//产品描述页的title，如产品规格，售后服务
	private String content;			//产品描述页的内容
	private String template_id;		//产品描述页的模板Id
	private String template_type;	//对应的模板对象类型，可能是一个商品
}
