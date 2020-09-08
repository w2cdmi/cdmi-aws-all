package pw.cdmi.msm.comment.rs;

import lombok.Data;

@Data
public class TestCommentRequest {
	private CommentTarget target;
	private String content;
	private Owner owner;
	
}
