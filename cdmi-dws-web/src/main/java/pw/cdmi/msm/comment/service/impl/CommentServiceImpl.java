package pw.cdmi.msm.comment.service.impl;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import pw.cdmi.msm.comment.model.SupportTargetType;
import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.repositories.CommentRepsitory;
import pw.cdmi.msm.comment.service.CommentService;
import pw.cdmi.msm.message.model.*;
import pw.cdmi.msm.message.model.entity.NotifyUserMessage;
import pw.cdmi.msm.message.service.MessageService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Component
@Transactional
public class CommentServiceImpl implements CommentService {
    private static Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentRepsitory commentRepsitory;
    @Autowired
    private MessageService messageService;

    @Override
    public Comment createComment(Comment comment) {
        comment.setPraiseNumber(0);
        comment.setCreateTime(new Date());
        Comment save = commentRepsitory.save(comment);
        List<Comment> listComment = new ArrayList<Comment>();

        try {
            send(save, listComment);
        } catch (Exception e) {
            log.error("create comment errer "+ JSONObject.fromObject(comment).toString());
            e.printStackTrace();
        }
        return save;
    }

    private NotifyUserMessage toNotifyUserMessage(Comment save) {
        MessageContent messageContent = new MessageContent();

        messageContent.setTitle("评论");

        messageContent.setContent(save.getUserName() + "回复了你");

        ReferEvent referEvent = new ReferEvent();

        EventContent eventContent = new EventContent();
        EventUser eventUser = new EventUser();
        EventObject eventObject = new EventObject();

        eventContent.setId(save.getId());
        eventContent.setText(save.getContent());
        eventContent.setType("Tenancy_Comment");

        eventObject.setId(save.getTargetId());
        eventObject.setType(save.getTargetType());

        eventUser.setId(save.getUserAid());
        eventUser.setName(save.getUserName());
        eventUser.setType(save.getUserType());
        eventUser.setHeadImageUrl(save.getHeadImage());

        referEvent.setUser(eventUser);
        referEvent.setContent(eventContent);
        referEvent.setTarget(eventObject);

        messageContent.setEvent(referEvent);

        NotifyUserMessage notifyUserMessage = NotifyUserMessage.messageContent(JSONObject.fromObject(messageContent).toString());

        notifyUserMessage.setAppId(save.getAppId());
        notifyUserMessage.setTenantId(save.getTenantId());
        notifyUserMessage.setSiteId(save.getSiteId());

        notifyUserMessage.setIsBroadcast(true);
        notifyUserMessage.setNotifyAid(save.getOwnerId());
        return notifyUserMessage;


    }

    private void send(Comment comment, List<Comment> listComment) {
        SupportTargetType fromName = SupportTargetType.fromName(comment
                .getTargetType());
        Comment comment2 = null;
        //是该用户没有发送过消息
        boolean juage = true;
        if (fromName == null) {
            throw new SecurityException("SupportTargetType is null");
        }

        Iterator<Comment> iterator = listComment.iterator();
        while (iterator.hasNext()) {
            Comment next = iterator.next();
            if (next.getOwnerId().equals(comment.getOwnerId())) {
                juage = false;
            }
        }

        if (juage && !StringUtils.isBlank(comment.getOwnerId()) && !comment.getOwnerId().equals(comment.getUserAid())) {

            messageService.createNotifyUserMessage(toNotifyUserMessage(comment));
            listComment.add(comment);
        }

        comment2 = commentRepsitory.findOne(comment.getTargetId());
        Comment comment3 = new Comment();
        if (comment2 != null) {
            comment3.setOwnerId(comment2.getOwnerId());

            //object
            comment3.setTargetId(comment2.getTargetId());
            comment3.setTargetType(comment2.getTargetType());

            //content
            comment3.setId(comment.getId());
            comment3.setContent(comment.getContent());

            //user
            comment3.setUserAid(comment.getUserAid());
            comment3.setUserName(comment.getUserName());
            comment3.setUserType(comment.getUserType());
            comment3.setHeadImage(comment.getHeadImage());
            //
            comment3.setAppId(comment2.getAppId());
            comment3.setTenantId(comment2.getTenantId());
            comment3.setSiteId(comment2.getSiteId());

            switch (fromName) {
                case Tenancy_Comment:

                    send(comment3, listComment);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public Iterator<Comment> commentList(Comment comment, int cursor, int maxcount) {
        // TODO Auto-generated method stub
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort sort = new Sort(order);
        Pageable pageRequest = new PageRequest(cursor, maxcount, sort);

        List<Comment> findAll = commentRepsitory.findByTargetIdAndTargetType(comment.getTargetId(), comment.getTargetType(), pageRequest);

        return findAll.iterator();

    }

    @Override
    public void deleteComment(Comment comment) {
        ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
        Comment findOne = commentRepsitory.findOne(Example.of(comment, matching));
        if (findOne == null) {
            throw new SecurityException("你没有权限");
        }
        try {
            messageService.deleteByTargetId(comment.getId());
        } catch (Exception e) {
            log.error("delete comment errer "+JSONObject.fromObject(comment).toString());
            e.printStackTrace();
        }

        Comment commentNext = new Comment();
        commentNext.setTargetId(comment.getId());

        Iterable<Comment> findAll = commentRepsitory.findAll(Example.of(commentNext, matching));
        Iterator<Comment> it = findAll.iterator();
        while (it.hasNext()) {
            Comment next = it.next();
            deleteComment(next);
        }

        commentRepsitory.delete(comment.getId());

    }

    @Override
    public long countComment(Comment comment) {


        long countByTargetIdAndTargetType = commentRepsitory.countByTargetIdAndTargetType(comment.getTargetId(), comment.getTargetType());
//		return countByTargetIdAndTargetType;

        return countByTargetIdAndTargetType;
    }

    @Override
    public boolean inspectExist(Comment comment) {
        ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
        if (commentRepsitory.findOne(Example.of(comment, matching)) == null)
            return true;
        return false;
    }

    @Override
    public Comment findComment(Comment comment) {
        ExampleMatcher matching = ExampleMatcher.matching().withIgnorePaths("praiseNumber");
        return commentRepsitory.findOne(Example.of(comment, matching));
    }

}
