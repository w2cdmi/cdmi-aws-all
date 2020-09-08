package pw.cdmi.msm.comment.rs;

import lombok.Data;

@Data
public class CommentRequest {
	private CommentTarget target;
	private String content;
	private String ownerId;
	
}
