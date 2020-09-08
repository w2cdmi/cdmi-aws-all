package pw.cdmi.msm.comment.rs.v1;

import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import pw.cdmi.core.exception.InvalidParameterException;
import pw.cdmi.msm.comment.model.SupportTargetType;
import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.rs.CommentRequest;
import pw.cdmi.msm.comment.rs.ListCommentResponse;
import pw.cdmi.msm.comment.rs.Owner;
import pw.cdmi.msm.comment.rs.TestCommentRequest;
import pw.cdmi.msm.comment.service.CommentService;

import java.util.*;

@RestController
@RequestMapping("comments/v1")
public class CommentResourceImpl {

    private static Logger log = LoggerFactory.getLogger(CommentResourceImpl.class);
    @Autowired
    private CommentService commentService;

    @PostMapping
    public @ResponseBody ListCommentResponse comment(@RequestBody CommentRequest comment) {
        // TODO Auto-generated method stub
        // 参数合法性检查

        if (comment == null || StringUtils.isBlank(comment.getOwnerId()) || comment.getTarget() == null
                || StringUtils.isBlank(comment.getTarget().getId())
                || StringUtils.isBlank(comment.getTarget().getType())) {
            // FIXME 修改为客户端必要参数缺失，请检查
            log.error("create comment param errer " + JSONObject.fromObject(comment).toString());
            throw new InvalidParameterException("参数错误");
        }

        // 检查type是否在支持列表中

        SupportTargetType support_target_type = SupportTargetType.fromName(comment.getTarget().getType());
        if (support_target_type == null) {
            // FIXME 修改为不支持的点赞目标类型
            log.error("create comment type errer " + JSONObject.fromObject(comment).toString());
            throw new NullPointerException("SupportTargetType is null");

        }
        Comment comment2 = new Comment();
        comment2.setUserAid(comment.getOwnerId());
        comment2.setContent(comment.getContent());
        comment2.setTargetId(comment.getTarget().getId());
        comment2.setTargetType(comment.getTarget().getType());

        comment2.setAppId("test");
        //包装点赞对象
        switch (support_target_type) {
            case Tenancy_File:

                //TODO 补充


                break;
            case Tenancy_Comment:
                //TODO 补充
                //		commentService.commentObject(comment2);

                break;
            case Tenancy_User:

                break;
            default:
                throw new SecurityException();
        }
        log.info("create comment " + JSONObject.fromObject(comment).toString());
        return toListCommentResponse(commentService.createComment(comment2));
    }

    /**
     * 评论数目
     *
     * @param id
     * @param type
     * @return
     */
    @GetMapping("/{target_id}/amount")
    public @ResponseBody
    Map<String, Object> getCommentAmount(@PathVariable("target_id") String id, @RequestParam("type") String type) {
        // TODO 参数合法性检查
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            // FIXME 修改为客户端必要参数缺失，请检
            throw new InvalidParameterException("参数错误");
        }
        Comment comment = new Comment();
        comment.setTargetId(id);
        comment.setTargetType(type);
        comment.setAppId("test");

        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("amount", commentService.countComment(comment));
        return hashMap;
    }

    @GetMapping("{target_id}/comment")
    public List<ListCommentResponse> listComment(@PathVariable("target_id") String id, @RequestParam("type") String type,
                                                 @RequestParam("cursor") int cursor, @RequestParam("maxcount") int maxcount) {
        // TODO 参数合法性检查
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            // FIXME 修改为客户端必要参数缺失，请检查
            throw new InvalidParameterException("参数错误");
        }
        Comment comment = new Comment();
        comment.setTargetId(id);
        comment.setTargetType(type);
        comment.setAppId("test");
        Iterator<Comment> commentIterator = commentService.commentList(comment, cursor, maxcount);
        ArrayList<ListCommentResponse> listCommentResponses = new ArrayList<ListCommentResponse>();
        while (commentIterator.hasNext()) {
            Comment next = commentIterator.next();
            ListCommentResponse listCommentResponse = toListCommentResponse(next);
            listCommentResponses.add(listCommentResponse);

        }
        return listCommentResponses;
    }

    /**
     * 评论列表，含二级
     *
     * @param id
     * @param type
     * @param cursor
     * @param maxcount
     * @return
     */
    @GetMapping("{target_id}/commentAndChildren")
    public List<ListCommentResponse> listCommentAndNext(@PathVariable("target_id") String id, @RequestParam("type") String type,
                                                        @RequestParam("cursor") int cursor, @RequestParam("maxcount") int maxcount, @ApiParam(required = false) @RequestParam(name = "nextmaxcount", required = false) Integer nextMaxCount) {
        // TODO 参数合法性检查
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            // FIXME 修改为客户端必要参数缺失，请检查
            throw new InvalidParameterException("参数错误");
        }
        //二级评论显示数
        if (nextMaxCount == null) {
            nextMaxCount = 4;
        }
        Comment comment = new Comment();
        comment.setTargetId(id);
        comment.setTargetType(type);
        comment.setAppId("test");


        Iterator<Comment> commentIterator = commentService.commentList(comment, cursor, maxcount);
        List<ListCommentResponse> listCommentResponses = new ArrayList<ListCommentResponse>();


        while (commentIterator.hasNext()) {

            Comment next = commentIterator.next();
            ListCommentResponse listCommentResponse = toListCommentResponse(next);
            //查询子评论实体
            Comment newcomment = new Comment();
            newcomment.setTargetId(next.getId());
            newcomment.setTargetType("Tenancy_Comment");
            //二级--
            Iterator<Comment> towCommentIterator = commentService.commentList(newcomment, 0, nextMaxCount);

            List<ListCommentResponse> children = new ArrayList<ListCommentResponse>();

            while (towCommentIterator.hasNext()) {
                Comment towNext = towCommentIterator.next();

                children.add(toListCommentResponse(towNext));
            }
            //----
            listCommentResponse.setChildren(children);
            listCommentResponses.add(listCommentResponse);

        }

        return listCommentResponses;
    }

    @DeleteMapping("/{id}")
    public void delComment(@RequestParam(value = "user_id") String userId, @PathVariable("id") String commentId) {
        //TODO 参数合法性检查
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(commentId)) {
            // FIXME 修改为客户端必要参数缺失，请检查
            throw new InvalidParameterException("参数错误");
        }


        Comment comment = new Comment();
        comment.setAppId("test");
        comment.setId(commentId);
        comment.setUserAid(userId);

        commentService.deleteComment(comment);
        log.info("detele comment id:" + commentId + "userid:" + userId);

    }

    private ListCommentResponse toListCommentResponse(Comment comment) {

        ListCommentResponse response = new ListCommentResponse();
        Comment newcomment = new Comment();
        newcomment.setTargetId(comment.getId());
        newcomment.setTargetType("Tenancy_Comment");
        long commentNumber = commentService.countComment(newcomment);
        response.setCommentNumber(commentNumber);
        response.setPraiseNumber(comment.getPraiseNumber());
        response.setContent(comment.getContent());
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        response.setCreate_time(simpleDateFormat.format(comment.getCreateTime()));
        response.setId(comment.getId());
        Owner owner = new Owner();
        owner.setId(comment.getUserAid());
        //TODO 评论人头像
        owner.setHeadImage(comment.getHeadImage());
        //TODO 评论人名字
        owner.setName(comment.getUserName());
        response.setOwner(owner);
        return response;
    }

    //---------------------------test
    @PostMapping("/test")
    public @ResponseBody ListCommentResponse testcomment(@RequestBody TestCommentRequest comment) {
        // TODO Auto-generated method stub
        // 参数合法性检查

        if (comment == null || comment.getTarget() == null || comment.getOwner() == null

                || StringUtils.isBlank(comment.getOwner().getId())
                || StringUtils.isBlank(comment.getTarget().getId())
                || StringUtils.isBlank(comment.getTarget().getType())
                || StringUtils.isBlank(comment.getTarget().getOwnerId())) {
            // FIXME 修改为客户端必要参数缺失，请检查
            log.error("Vtest create comment param errer " + JSONObject.fromObject(comment).toString());
            throw new InvalidParameterException("参数错误");
        }

        // 检查type是否在支持列表中

        SupportTargetType support_target_type = SupportTargetType.fromName(comment.getTarget().getType());
        if (support_target_type == null) {
            // FIXME 修改为不支持的点赞目标类型
            log.error("Vtest create comment type errer " + JSONObject.fromObject(comment).toString());
            throw new NullPointerException("SupportTargetType is null");

        }
        Comment comment2 = new Comment();
        comment2.setUserAid(comment.getOwner().getId());
        comment2.setUserName(comment.getOwner().getName());
        comment2.setHeadImage(comment.getOwner().getHeadImage());
        comment2.setContent(comment.getContent());
        comment2.setTargetId(comment.getTarget().getId());
        comment2.setTargetType(comment.getTarget().getType());
        comment2.setOwnerId(comment.getTarget().getOwnerId());

        comment2.setAppId("test");
        //包装点赞对象

        Comment save = commentService.createComment(comment2);

        log.info("Vtest create comment " + JSONObject.fromObject(save).toString());
        return toListCommentResponse(save);
    }

    @GetMapping("/comment/{id}")
    public Map<String, Object> target(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new SecurityException("id is null");
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        Comment comment = new Comment();
        comment.setId(id);
        Comment findComment = commentService.findComment(comment);
        if (findComment != null) {
            hashMap.put("targetId", findComment.getTargetId());
            hashMap.put("targeType", findComment.getTargetType());
            hashMap.put("content", findComment.getContent());
            hashMap.put("createTime", findComment.getCreateTime().getTime());
        }
        return hashMap;
    }


}
