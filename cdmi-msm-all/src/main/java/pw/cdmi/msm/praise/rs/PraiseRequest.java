package pw.cdmi.msm.praise.rs;

import lombok.Data;
import pw.cdmi.msm.praise.model.PraiseTarget;

@Data
public class PraiseRequest {
	private PraiseTarget  target;
	private String ownerId;
	
}
