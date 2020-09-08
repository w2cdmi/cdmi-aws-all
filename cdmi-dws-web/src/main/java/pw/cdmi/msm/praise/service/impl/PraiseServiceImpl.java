package pw.cdmi.msm.praise.service.impl;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pw.cdmi.msm.message.model.*;
import pw.cdmi.msm.message.model.entity.NotifyUserMessage;
import pw.cdmi.msm.message.service.MessageService;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.repositories.PraiseRepsitory;
import pw.cdmi.msm.praise.service.PraiseService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
@Transactional
public class PraiseServiceImpl implements PraiseService {

    private static Logger logger = LoggerFactory.getLogger(PraiseServiceImpl.class);
    @Inject
    private PraiseRepsitory praiseRepsitory;

    @Autowired
    private MessageService messageService;

    @Override
    public String createPraise(Praise praise) {
        //FIXME 自动创建包含当前用户请求环境信息，并设置到对象中。

        praise.setCreateTime(new Date());
        Praise save = null;
        try {
            save = praiseRepsitory.save(praise);
            if (!StringUtils.isBlank(praise.getOwnerId()) && !praise.getOwnerId().equals(praise.getUserAid())) {
                messageService.createNotifyUserMessage(toNotifyUserMessage(praise));
            }

        } catch (Exception e) {
            logger.error("create praise errer" + JSONObject.fromObject(praise).toString());
            e.printStackTrace();
            return null;
        }
        return save.getId();

    }

    private NotifyUserMessage toNotifyUserMessage(Praise save) {
        MessageContent messageContent = new MessageContent();

        messageContent.setTitle("点赞");

        messageContent.setContent(save.getUserName() + "赞了你");

        ReferEvent referEvent = new ReferEvent();

        EventContent eventContent = new EventContent();
        EventUser eventUser = new EventUser();
        EventObject eventObject = new EventObject();

        eventContent.setId(save.getId());
        eventContent.setType("Tenancy_Praise");

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

    @Override
    public long getPrainseNumber(Praise praise) {

        return praiseRepsitory.countByTargetIdAndTargetType(praise.getTargetId(), praise.getTargetType());
    }

    @Override
    public Iterator<Praise> listPraiser(Praise praise, int cursor, int maxcount) {
        //FIXME 根据认证条件自动组成查询范围条件


        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort sort = new Sort(order);
        Pageable pageRequest = new PageRequest(cursor, maxcount, sort);


        List<Praise> findAll = praiseRepsitory.findByTargetIdAndTargetType(praise.getTargetId(), praise.getTargetType(), pageRequest);

        return findAll.iterator();

    }

    @Override
    public Praise inspectExist(Praise praise) {

        return praiseRepsitory.findByTargetIdAndUserAidAndTargetType(praise.getTargetId(), praise.getUserAid(), praise.getTargetType());

    }

    @Override
    public void deletePraise(Praise praise) {
        Praise findOne = praiseRepsitory.findOne(Example.of(praise));

        if (findOne == null) {
            logger.debug("delete praise errer 没有目标" + JSONObject.fromObject(praise).toString());
            throw new SecurityException("没有删除目标");
        }
        messageService.deleteByTargetId(findOne.getId());

        praiseRepsitory.delete(praise.getId());
    }

    @Override
    public Praise findOne(Praise praise) {

        return praiseRepsitory.findOne(Example.of(praise));
    }


}
