package pw.cdmi.msm.praise.rs.v1;

import io.vertx.core.json.JsonObject;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pw.cdmi.core.exception.InvalidParameterException;
import pw.cdmi.msm.comment.model.entities.Comment;
import pw.cdmi.msm.comment.repositories.CommentRepsitory;
import pw.cdmi.msm.praise.model.SupportTargetType;
import pw.cdmi.msm.praise.model.entities.Praise;
import pw.cdmi.msm.praise.rs.ListPraiserResponse;
import pw.cdmi.msm.praise.rs.PraiseRequest;
import pw.cdmi.msm.praise.rs.TestPraiseRequest;
import pw.cdmi.msm.praise.service.PraiseService;

import java.util.*;

//import pw.cdmi.core.http.exception.AWSClientException;
//import pw.cdmi.core.http.exception.AWSServiceException;
//import pw.cdmi.core.http.exception.SystemReason;
//import pw.cdmi.msm.geo.ClientReason;

//import pw.cdmi.open.ClientError;
@RestController
@RequestMapping("/praise/v1")
public class PraiseResourceImpl {

    private static Logger log = LoggerFactory.getLogger(PraiseResourceImpl.class);
    @Autowired
    private PraiseService praiseService;
    @Autowired
    private CommentRepsitory commentService;


    @PostMapping
    public @ResponseBody
    Map<String, Object> praise(@RequestBody PraiseRequest praise) {
        //TODO 参数合法性检查

        if (praise == null || praise.getTarget() == null
                || StringUtils.isBlank(praise.getTarget().getId())
                || StringUtils.isBlank(praise.getTarget().getType())
                || StringUtils.isBlank(praise.getOwnerId())
                ) {
            // FIXME 修改为客户端必要参数缺失，请检查
            log.error("create praise prarm errer " + JSONObject.fromObject(praise).toString());
            throw new InvalidParameterException("参数错误");
        }

        //TODO 检查type是否在支持列表中

        SupportTargetType support_target_type = SupportTargetType.fromName(praise.getTarget().getType());
        if (support_target_type == null) {
            // FIXME 修改为不支持的点赞目标类型
            log.error("create praise type errer " + JSONObject.fromObject(praise).toString());
            throw new NullPointerException("SupperTargetType is null");
        }
        //包装点赞对象
        Praise praise2 = new Praise();
        praise2.setUserAid(praise.getOwnerId());
        praise2.setAppId("test");
        praise2.setTargetId(praise.getTarget().getId());
        praise2.setTargetType(praise.getTarget().getType());
        // 根据点赞目标类型，判断点赞目标对象是否存在

        switch (support_target_type) {
            case Tenancy_File:

                //TODO
                //该用户是否已经对该租户文件有点赞信息
                if (praiseService.inspectExist(praise2) != null) {
                    // FIXME 已经存在该用户对该租户文件点赞记录
                    log.info("Already a little praise " + JSONObject.fromObject(praise).toString());
                    throw new SecurityException("已经点赞");
                }

                break;
            case Tenancy_Comment:

                //TODO 该用户是否已经对该租户文件有点赞信息
                if (praiseService.inspectExist(praise2) != null) {
                    // FIXME 已经存在该用户对该租户文件点赞记录
                    log.info("Already a little praise " + JSONObject.fromObject(praise).toString());
                    throw new SecurityException("已经点赞");
                }
                //TODO 记录评论的点赞数
                Comment findOne = commentService.findOne(praise.getTarget().getId());
                if (findOne != null) {
                    findOne.setPraiseNumber(findOne.getPraiseNumber() + 1);
                    commentService.save(findOne);
                }

                break;
            case Tenancy_User:
                // 获取租户用户，並鎖定刪除，以及鎖定當前操作用戶刪除

                // 如果租戶用戶存在，新增一個點贊信息，釋放鎖定刪除
                break;
            default:
                //	throw new AWSServiceException(SystemReason.UnImplemented);
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("newid", praiseService.createPraise(praise2));
        return hashMap;

    }


    @GetMapping(value = "/{target_id}/amount")
    public @ResponseBody
    Map<String, Long> getPraiseAmount(@PathVariable("target_id") String id, @RequestParam("type") String type) {
        //TODO 参数合法性检查
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            // FIXME 修改为客户端必要参数缺失，请检查
            log.error("get praise amount prarm errer ");
            throw new InvalidParameterException("参数错误");
        }
        Praise praise = new Praise();
        praise.setTargetId(id);
        praise.setTargetType(type);
        praise.setAppId("test");
        Map<String, Long> hashMap = new HashMap<String, Long>();
        hashMap.put("amount", praiseService.getPrainseNumber(praise));
        return hashMap;

    }

    @GetMapping(value = "/{target_id}/praiser")
    public @ResponseBody
    List<ListPraiserResponse> listPraiser(@PathVariable("target_id") String id, @RequestParam("type") String type,
                                          @RequestParam("cursor") int cursor, @RequestParam("maxcount") int maxcount) {
        //TODO 参数合法性检查
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            // FIXME 修改为客户端必要参数缺失，请检查
            log.error("get praises prarm errer ");
            throw new InvalidParameterException("参数错误");
        }
        Praise praise = new Praise();
        praise.setTargetId(id);
        praise.setTargetType(type);
        praise.setAppId("test");
        //获取praise list
        Iterator<Praise> listPraiser = praiseService.listPraiser(praise, cursor, maxcount);

        //转换
        return toListPraiserResponse(listPraiser);
    }

    @GetMapping(value = "/users/{userId}/praised/{target_id}")
    public @ResponseBody
    Map<String, Object> inspectExist(@PathVariable("userId") String userId, @PathVariable("target_id") String id, @RequestParam("type") String type) {
        //TODO 参数合法性检查
        if (StringUtils.isBlank(id) || StringUtils.isBlank(type)) {
            // FIXME 修改为客户端必要参数缺失，请检
            throw new InvalidParameterException("参数错误");
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();

        Praise praise = new Praise();
        praise.setUserAid(userId);
        praise.setAppId("test");
        praise.setTargetId(id);
        praise.setTargetType(type);

        Praise inspectExist = praiseService.inspectExist(praise);

        hashMap.put("praised", inspectExist != null);
        if (inspectExist != null) {
            hashMap.put("id", inspectExist.getId());
        }
        return hashMap;
    }

    @DeleteMapping(value = "/{id}")
    public void deletePraise(@PathVariable("id") String praiseId, @RequestParam("user_id") String userId) {

        if (StringUtils.isBlank(praiseId) || StringUtils.isBlank(userId)) {
            log.error("detele praise prarm errer");
            throw new InvalidParameterException("参数错误");
        }
        Praise praise = new Praise();
        praise.setAppId("test");
        praise.setId(praiseId);
        praise.setUserAid(userId);
        Praise findOne = praiseService.findOne(praise);
        if (findOne == null) {
            throw new SecurityException("目标不存在");
        }
        SupportTargetType type = SupportTargetType.fromName(findOne.getTargetType());
        switch (type) {
            case Tenancy_Comment:
                Comment comment = commentService.findById(findOne.getTargetId());
                comment.setPraiseNumber(comment.getPraiseNumber() - 1);
                commentService.save(comment);
                break;

            default:
                break;
        }
        log.info("delete praise Id:" + praiseId + "userid:" + userId);
        praiseService.deletePraise(findOne);
    }

    /**
     * 转化器
     *
     * @param iterator
     * @return
     */
    private List<ListPraiserResponse> toListPraiserResponse(Iterator<Praise> iterator) {
        //TODO 转化

        List<ListPraiserResponse> list = new ArrayList<ListPraiserResponse>();
        while (iterator.hasNext()) {

            ListPraiserResponse response = new ListPraiserResponse();
            Praise next = iterator.next();
            response.setId(next.getUserAid());
            response.setHeadImage(next.getHeadImage());
            response.setName(next.getUserName());
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setCreateTime(simpleDateFormat.format(next.getCreateTime()));
            list.add(response);
        }
        return list;
    }

    //-------------------------------------test
    @PostMapping("/test")
    public @ResponseBody
    HashMap<String, Object> testpraise(@RequestBody TestPraiseRequest praise) {
        //TODO 参数合法性检查

        if (praise == null || praise.getTarget() == null || praise.getOwner() == null
                || StringUtils.isBlank(praise.getTarget().getId())
                || StringUtils.isBlank(praise.getTarget().getType())
                || StringUtils.isBlank(praise.getOwner().getId())
                || StringUtils.isBlank(praise.getTarget().getOwnerId())
                ) {
            // FIXME 修改为客户端必要参数缺失，请检查
            throw new InvalidParameterException("参数错误");
        }

        //TODO 检查type是否在支持列表中

        SupportTargetType support_target_type = SupportTargetType.fromName(praise.getTarget().getType());
        if (support_target_type == null) {
            // FIXME 修改为不支持的点赞目标类型
            throw new NullPointerException("SupperTargetType is null");
        }
        //包装点赞对象
        Praise praise2 = new Praise();
        praise2.setUserAid(praise.getOwner().getId());
        praise2.setUserName(praise.getOwner().getName());
        praise2.setHeadImage(praise.getOwner().getHeadImage());
        praise2.setAppId("test");
        praise2.setTargetId(praise.getTarget().getId());
        praise2.setOwnerId(praise.getTarget().getOwnerId());
        praise2.setTargetType(praise.getTarget().getType());
        // 根据点赞目标类型，判断点赞目标对象是否存在
        synchronized (this) {
            switch (support_target_type) {
                case Tenancy_File:

                    //TODO
                    //该用户是否已经对该租户文件有点赞信息
                    if (praiseService.inspectExist(praise2) != null) {
                        // FIXME 已经存在该用户对该租户文件点赞记录
                        throw new SecurityException("已经点赞");
                    }

                    break;
                case Tenancy_Comment:

                    //TODO 该用户是否已经对该租户文件有点赞信息
                    if (praiseService.inspectExist(praise2) != null) {
                        // FIXME 已经存在该用户对该租户文件点赞记录
                        throw new SecurityException("已经点赞");
                    }
                    //TODO 记录评论的点赞数
                    Comment findOne = commentService.findById(praise.getTarget().getId());
                    if (findOne == null) {
                        throw new SecurityException("没有评论信息");
                    }
                    findOne.setPraiseNumber(findOne.getPraiseNumber() + 1);
                    commentService.save(findOne);

                    break;
                case Tenancy_User:
                    // 获取租户用户，並鎖定刪除，以及鎖定當前操作用戶刪除

                    // 如果租戶用戶存在，新增一個點贊信息，釋放鎖定刪除
                    break;
                default:
                    //	throw new AWSServiceException(SystemReason.UnImplemented);
            }
            String createPraise = praiseService.createPraise(praise2);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("newid", createPraise);
            return hashMap;
        }
    }

    @GetMapping("/praise/{id}/target")
    public Map<String, Object> target(@PathVariable("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new SecurityException("id is null");
        }
        Map<String, Object> hashMap = new HashMap<String, Object>();
        Praise praise = new Praise();
        praise.setId(id);
        Praise findPraise = praiseService.findOne(praise);
        if (findPraise != null) {
            hashMap.put("targetId", findPraise.getTargetId());
            hashMap.put("targeType", findPraise.getTargetType());
        }
        return hashMap;
    }

}
