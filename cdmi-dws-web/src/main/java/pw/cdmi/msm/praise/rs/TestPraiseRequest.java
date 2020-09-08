package pw.cdmi.msm.praise.rs;

import lombok.Data;
import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.praise.model.PraiseTarget;
@Data
public class TestPraiseRequest {
	private PraiseTarget  target;
	private Owner owner;
	
}
