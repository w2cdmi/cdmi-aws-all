package pw.cdmi.msm.product.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.radio.model.MultiAppSiteModel;

@Data
@EqualsAndHashCode(callSuper=false)
public class IntroduceTemplate extends MultiAppSiteModel{
	private String name; 				//模板的名称
	private String content;				//模板的内容
	private String[] labels;			//模板内的标签
}
