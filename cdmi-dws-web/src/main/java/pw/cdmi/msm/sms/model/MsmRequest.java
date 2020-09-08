package pw.cdmi.msm.sms.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MsmRequest {
	@ApiModelProperty(value = "电话号码",required = false,example = "18081527466")
	private String mobile;
	@ApiModelProperty(value = "短信模板id",required = true)
	private String templatesId;
	@ApiModelProperty(value = "签名模板",required = true)
	private SignTypeSupper signType;
}
