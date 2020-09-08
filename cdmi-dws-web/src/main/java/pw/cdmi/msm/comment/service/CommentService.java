package pw.cdmi.msm.comment.service;

import java.util.Iterator;
import java.util.List;


import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.rs.ListCommentResponse;


public interface CommentService {

    /**
     * 查询对应id的comment
     *
     * @param CommentId
     * @return
     */
    public Comment findComment(Comment comment);

    /**
     * 保存评论
     *
     * @param target
     */
    public Comment createComment(Comment comment);

    /**
     * 获取对应目标的all评论
     *
     * @param target_id
     * @return
     */
    public Iterator<Comment> commentList(Comment comment, int cursor, int maxcount);

    /**
     * 删除对应评论
     *
     * @param Coment_id
     */
    public void deleteComment(Comment comment);

    /**
     * 目标所有的评论
     *
     * @param target_id
     * @return
     */
    public long countComment(Comment comment);

    /**
     * 判断评论是否存在
     *
     * @param id
     * @param type
     * @return
     */
    public boolean inspectExist(Comment comment);

}
