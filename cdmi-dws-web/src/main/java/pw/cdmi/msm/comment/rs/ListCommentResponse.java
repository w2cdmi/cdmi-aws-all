package pw.cdmi.msm.comment.rs;

import lombok.Data;

import java.util.List;

@Data
public class ListCommentResponse {
    private String id;
    private Owner owner;
    private long  praiseNumber;
    private long  commentNumber;
    private String content;
    private String create_time;
    private List<ListCommentResponse> children;
}

