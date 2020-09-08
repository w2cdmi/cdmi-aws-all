package pw.cdmi.msm.question.rs.v1;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

/**
 * **********************************************************
 * <br/>
 * 咨询问题的接口
 * @author wsl
 * @version cdm Service Platform, 2016年6月21日
 ***********************************************************
 */
@Path("/faq/v1")
public interface QuestionResource {
    /**
     * 
     * 向网站或个人提交一个问题(当参数中的Receiver_Id为空就向网站提交，有则向指定用户提交)
     * @param questionString
     * @return
     */
    @POST
    @Path("/question")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject addQuestionToWebOrUser(String questionString);

    /**
     * 
     *  修改或补充自己提出的问题
     * @param id
     * @param title
     * @param content
     * @param ContactInfo
     * @param Owner_Id
     * @return
     */
    @PUT
    @Path("/question/{id}/{title}/{content}/{ContactInfo}/{Owner_Id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateSelfQuestion(@PathParam("id") String id, @PathParam("title") String title,
        @PathParam("content") String content, @PathParam("ContactInfo") String ContactInfo,
        @PathParam("Owner_Id") String Owner_Id);

    /**
     * 
     * 关闭用户提出的问题,可由自己和管理员调用，根据传入的操作人员id
     * 
     * @return
     */
    @PUT
    @Path("/question/{question_id}/closed")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject closeQuestion(@PathParam("question_id") String question_id);

    /**
     * 
     * 标记自己的问题已经得到解决
     * @return
     */
    @PUT
    @Path("/question/{question_id}/resolved")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject questionResolvedTag(@PathParam("question_id") String question_id);

    /**
     * 为问题创建回复
     * @param questionId
     * @return
     */
    @POST
    @Path("/question/{question_id}/reply")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject replyQuestion(@PathParam("questionId") String questionId, String questionReplyString);

    /**
     * 
     * 向网站添加一条常见问题
     * @param questionCommonString
     * @return
     */
    @POST
    @Path("/question/common")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject addQuestionCommonToWeb(String questionCommonString);

    /**
     * 修改指定的常见问题
     * @return
     */
    @PUT
    @Path("/question/common/{common_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateQuestionCommonById();

    /**
     * 选择指定的回复作为问题的采纳答案
     * @param questionId
     * @param replyId
     * @return
     */
    @PUT
    @Path("/question/{question_id}/answer/{reply_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject chooseReplyToQuestion(@PathParam("questionId") String questionId,
        @PathParam("replyId") String replyId);

    /**
     * 
     * 设置指定的问题为常见问题
     * @param questionId
     * @param replyId
     * @return
     */
    @PUT
    @Path("/question/{question_id}/{reply_id}/common")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject setCommonQuestion(@PathParam("questionId") String questionId, @PathParam("replyId") String replyId);

    /**
     * 删除一条指定的常见问题
     * @param commonId
     * @return
     */
    @DELETE
    @Path("/question/common/{common_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteCommonById(@PathParam("commonId") String commonId);

    /**
     * 
     * 查询用户自己提出的问题列表
     * @return
     */
    @GET
    @Path("/questions/my")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionByUser();

    /**
     * 
     * 查询用户有新回复的问题列表
     * @return
     */
    @GET
    @Path("/questions/my_new_replied")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionHasNewReply();

    /**
     * 
     * 查询用户未解决的问题列表
     * @return
     */
    @GET
    @Path("/questions/my_unresolved")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionUnresolved();

    /**
     * 
     * 查询用户已解决的问题列表
     * @return
     */
    @GET
    @Path("/questions/my_resolved")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionIsResolved();

    /**
     * 
     * 查询用户已关闭的问题列表
     * @return
     */
    @GET
    @Path("/questions/my_closed")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionIsClosed();

    /**
     *  获取网站中指定问题分类下的常见咨询问题列表(classId如果为空则表示查询所有常见问题)
     * @param classId
     * @return
     */
    @GET
    @Path("/questions/common")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findCommonByClassId(@PathParam("classId") String classId);

    /**
     *  获取网站中指定问题分类下的问题列表(classId如果为空则表示查询网站中所有问题)
     * @param classId
     * @return
     */
    @GET
    @Path("/questions")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionByClassId(@PathParam("classId") String classId);

    /**
     * 
     * 管理员获得指定问题分类下用户提出的待回复的问题列表(classId可为空)
     * @param classId
     * @return
     */
    @GET
    @Path("/questions/new")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionByClassIdAndNew(@PathParam("classId") String classId);

    /**
     * 
     * 管理员获得指定问题分类下用户提出的已解决的问题列表(classId可为空)
     * @param classId
     * @return
     */
    @GET
    @Path("/questions/resolved")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionByClassIdAndResolved(@PathParam("classId") String classId);

    /**
     * 管理员获得指定用户指定问题分类下提出的问题列表(classId可为空，为空代表获取所有问题)
     * @param userId
     * @param classId
     * @return
     */
    @GET
    @Path("/questions/{user_Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionByUSerIdAndClassId(@PathParam("userId") String userId,
        @PathParam("classId") String classId);

    /**
     * 获得指定的问题以及回复
     * @param questionId
     * @return
     */
    @GET
    @Path("/question/{question_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findQuestionAndReplyById(@PathParam("questionId") String questionId);

    /**
     * 
     * 获取指定问题的回复列表
     * @param questionId
     * @return
     */
    @GET
    @Path("question/{question_id}/replies")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findReplyByQuestionId(@PathParam("questionId") String questionId);

    /**
     * 为系统创建一个咨询问题的模块类型
     * @param questionModelString
     * @return
     */
    @POST
    @Path("/question/model")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject addQuestionModel(String questionModelString);

    /**
     * 
     * 修改指定的咨询问题模型
     * @param questionModelString
     * @return
     */
    @PUT
    @Path("/question/model/{model_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateQuestionModelById(String questionModelString);

    /**
     * 
     * 删除指定的咨询问题模块
     * @param ModelId
     * @return
     */
    @DELETE
    @Path("question/model/{model_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteModelById(@PathParam("modelId") String ModelId);

    /**
     * 
     * 删除一条问题的分类信息
     * @param classId
     * @return
     */
    @DELETE
    @Path("question/class/{class_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteQuestionClassById(@PathParam("classId") String classId);

    /**
     * 新建一条问题分类信息
     * @param questionClassString
     * @return
     */
    @POST
    @Path("/question/class")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject addQuestionClass(String questionClassString);

    /**
     * 编辑一条问题分类信息
     * @param questionClassString
     * @return
     */
    @PUT
    @Path("/question/class/{class_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateQuestionClass(@PathParam("classId") String classId, String questionClassString);

    /**
     * 根据问题标题模糊查询用户的问题列表
     * @param questionClassString
     * @return
     */
    @GET
    @Path("/{title}/questions")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject findQuestionByTitle(@PathParam("title") String title);

    /**
     *  删除自己发布的一个问题回复
     * @param replyId
     * @return
     */
    @DELETE
    @Path("/question/reply/{reply_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteReplyById(@PathParam("replyId") String replyId);

    /**
     * 删除一条问题
     * @param replyId
     * @return
     */
    @DELETE
    @Path("/question/{question_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteQuestionById(@PathParam("questionId") String questionId);

    /**
     * 删除指定问题的指定回复
     * @param replyId
     * @return
     */
    @DELETE
    @Path("/question/{question_id}/reply/{reply_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteReplyByQuestionIdAndReplyId(@PathParam("questionId") String questionId,
        @PathParam("replyId") String replyId);

    /**
     * 
     * 为问题回复投赞成票
     * @param replyId
     * @param agreeNumber
     * @return
     */
    @PUT
    @Path("/question/reply/{reply_id}/agreenumber")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateReplyByAgreeNum(@PathParam("replyId") String replyId,
        @PathParam("agreeNumber") String agreeNumber);

    /**
     * 为问题回复投否定票
     * @param replyId
     * @param agreeNumber
     * @return
     */
    @PUT
    @Path("/question/reply/{reply_id}/disnumber")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateReplyByDisagreeNum(@PathParam("replyId") String replyId,
        @PathParam("disagreeNumber") String disagreeNumber);

    /**
     * 管理员上移常见问题排序
     * @param questionCommonId
     * @return
     */
    @PUT
    @Path("/question/questioncommon/{questioncommon_id}/orderup")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject moveUpCommonQuestion(@PathParam("questionCommonId") String questionCommonId);

    /**
     * 管理员下移常见问题排序
     * @param questionCommonId
     * @return
     */
    @PUT
    @Path("/question/questioncommon/{questioncommon_id}/orderdown")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject moveDownCommonQuestion(@PathParam("questionCommonId") String questionCommonId);

    /**
     * 设置用户所提问题的关联对象
     * @param questionId
     * @param RefObject
     * @param refObjectId
     * @return
     */

    @PUT
    @Path("/question/{question_id}/addrefobject")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject setRefObjectToQuestion(@PathParam("questionId") String questionId,
        @PathParam("refObject") String RefObject, @PathParam("refObjectId") String refObjectId);

    /**
     * 设置常见问题的关联对象
     * @param questionId
     * @param RefObject
     * @param refObjectId
     * @return
     */

    @PUT
    @Path("/question/questioncommon/{questioncommon_id}/addrefobject")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject setRefObjectToCommon(@PathParam("questioncommonId") String questioncommonId,
        @PathParam("refObject") String RefObject, @PathParam("refObjectId") String refObjectId);

    /**
     * 
     * 将指定问题移动到其他分类下
     * @param questionId
     * @param classId
     * @return
     */
    @PUT
    @Path("/question/{question_id}/class/{class_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject moveQuestionToClass(@PathParam("questionId") String questionId,
        @PathParam("classId") String classId);

    /**
     * 
     * 将指定分类下用户提问批量移动到其他分类下 
     * @param classId
     * @param targetclassId
     * @return
     */
    @PUT
    @Path("/questions/class/{class_id}/targetclass/{targetclass_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject changeQuestionClassId(@PathParam("classId") String classId,
        @PathParam("targetclassId") String targetclassId);

    /**
     * 
     * 将指定常见问题移动到其他分类下
     * @param questionId
     * @param classId
     * @return
     */
    @PUT
    @Path("/question/common/{common_id}/class/{class_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject moveQuestionCommonToClass(@PathParam("commonId") String questionId,
        @PathParam("classId") String classId);

    /**
     * 
     * 将指定分类下常见问题批量移动到其他分类下 
     * @param classId
     * @param targetclassId
     * @return
     */
    @PUT
    @Path("/question/common/{common_id}/class/{class_id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject changeQuestionCommonClassId(@PathParam("classId") String classId,
        @PathParam("targetclassId") String targetclassId);

}
