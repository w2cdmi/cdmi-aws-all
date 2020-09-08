package pw.cdmi.msm.question.rs.v1;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.ClientReason;
import pw.cdmi.core.http.exception.GlobalHttpClientError;
import pw.cdmi.core.http.utils.RequestParameterHandleUtils;
import pw.cdmi.msm.question.model.QuestionStatus;
import pw.cdmi.msm.question.model.entities.Question;
import pw.cdmi.msm.question.model.entities.QuestionClass;
import pw.cdmi.msm.question.model.entities.QuestionCommon;
import pw.cdmi.msm.question.model.entities.QuestionModel;
import pw.cdmi.msm.question.model.entities.QuestionReply;
import pw.cdmi.msm.question.service.QuestionClassService;
import pw.cdmi.msm.question.service.QuestionCommonService;
import pw.cdmi.msm.question.service.QuestionModelService;
import pw.cdmi.msm.question.service.QuestionReplyService;
import pw.cdmi.msm.question.service.QuestionService;

@Service("questionResource")
public class QuestionResourceImpl implements QuestionResource {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionReplyService questionReplyService;

    @Autowired
    private QuestionCommonService questionCommonService;

    @Autowired
    private QuestionClassService questionClassService;

    @Autowired
    private QuestionModelService questionModelService;

    @Resource
    private RequestParameterHandleUtils requestParameterHandleUtils;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public JSONObject addQuestionToWebOrUser(String questionString) {
        Question question = new Question();
        question = requestParameterHandleUtils.convertRequestParams2Entity(question, questionString);
        if (question == null) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        question.setCreateDate(new Date());
        question.setStatus(QuestionStatus.New);
        try {
            questionService.addQuestion(question);
            jsonObject.put("success", true);
        } catch (Exception e) {
            jsonObject.put("success", false);
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateSelfQuestion(String id, String title, String content, String ContactInfo, String Owner_Id) {
        if (StringUtils.isBlank(id) || StringUtils.isBlank(content)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        Question question = questionService.getQuestion(id);
        if (question == null) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        } else {
            if (StringUtils.isNotBlank(Owner_Id)) {
                if (Owner_Id.equals(question.getSiteUserId())) {
                    throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
                }
            }
        }
        if (StringUtils.isNotBlank(title)) {
            question.setTitle(title);
        }
        if (StringUtils.isNotBlank(ContactInfo)) {
            question.setTitle(ContactInfo);
        }
        question.setContent(question.getContent() + content);
        question.setUpdateDate(new Date());
        JSONObject jsonObject = new JSONObject();
        try {
            questionService.updateQuestion(question);
            jsonObject.put("success", true);
        } catch (Exception e) {
            jsonObject.put("success", false);
        }
        return jsonObject;

    }

    @Override
    public JSONObject replyQuestion(String questionId, String questionReplyString) {
        if (StringUtils.isBlank(questionId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        QuestionReply questionReply = new QuestionReply();
        questionReply = requestParameterHandleUtils.convertRequestParams2Entity(questionReply, questionReplyString);
        if (StringUtils.isBlank(questionReply.getContent())) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        questionReply.setCreateDate(new Date());
        JSONObject jsonObject = new JSONObject();
        try {
            questionReplyService.createQuestionReply(questionReply);
            Question question = questionService.getQuestion(questionId);
            question.setStatus(QuestionStatus.Replied);
            questionService.updateQuestion(question);
            jsonObject.put("successs", true);
        } catch (Exception e) {
            jsonObject.put("success", false);
        }
        return jsonObject;
    }

    @Override
    public JSONObject closeQuestion(String questionId) {
        if (StringUtils.isBlank(questionId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            questionService.closeQuestion(questionId);
            jsonObject.put("success", true);
        } catch (Exception e) {
            jsonObject.put("success", true);
        }
        return jsonObject;
    }

    @Override
    public JSONObject questionResolvedTag(String questionId) {
        if (StringUtils.isBlank(questionId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            questionService.changeStatus(questionId);
            jsonObject.put("success", true);
        } catch (Exception e) {
            jsonObject.put("success", true);
        }
        return jsonObject;
    }

    @Override
    public JSONObject addQuestionCommonToWeb(String questionCommonString) {
        QuestionCommon questionCommon = new QuestionCommon();
        questionCommon = requestParameterHandleUtils.convertRequestParams2Entity(questionCommon, questionCommonString);
        if (questionCommon == null) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        questionCommon.setCreateDate(new Date());
        try {
            questionCommonService.addQuestionCommon(questionCommon);
            jsonObject.put("success", true);
        } catch (Exception e) {
            jsonObject.put("success", false);
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateQuestionCommonById() {

        return null;
    }

    @Override
    public JSONObject chooseReplyToQuestion(String questionId, String replyId) {
        if (StringUtils.isBlank(questionId) || StringUtils.isBlank(replyId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        Question question = questionService.getQuestion(questionId);
        QuestionReply reply = questionReplyService.getQuestionReply(replyId);
        JSONObject jsonObject = new JSONObject();
        if (question == null || reply == null) {
            throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);
        }
        reply.setIsAccepted(true);
        try {
            questionReplyService.updateQuestionReply(reply);
            jsonObject.put("success", "修改成功");
        } catch (Exception e) {
            jsonObject.put("success", "修改失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject setCommonQuestion(String questionId, String replyId) {
        if (StringUtils.isBlank(questionId) || StringUtils.isBlank(replyId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        Question question = questionService.getQuestion(questionId);
        QuestionReply reply = questionReplyService.getQuestionReply(replyId);
        JSONObject jsonObject = new JSONObject();
        if (question == null || reply == null) {
            throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);
        }
        if (questionCommonService.isTitleRepeat(question.getTitle())) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        QuestionCommon common = new QuestionCommon();
        common.setModelId(question.getModelId());
        common.setClassId(question.getClassId());
        common.setTitle(question.getTitle());
        common.setContent(question.getContent());
        common.setAnswerContent(reply.getContent());
        common.setReadNumber(question.getReadNumber());
        common.setOrderNumber(question.getOrderNumber());
        common.setRefObject(question.getRefObject());
        common.setRefObjectId(question.getRefObjectId());
        ;
        common.setCreateDate(new Date());
        common.setSiteId(question.getSiteId());
        try {
            questionCommonService.addQuestionCommon(common);
            jsonObject.put("success", "添加成功");
        } catch (Exception e) {
            jsonObject.put("success", "添加失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject deleteCommonById(String commonId) {
        if (StringUtils.isBlank(commonId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            questionCommonService.deleteQuestionCommon(commonId);
            jsonObject.put("success", "删除成功");
        } catch (Exception e) {
            jsonObject.put("success", "删除失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject findQuestionByUser() {
        
        return null;
    }

    @Override
    public JSONObject findQuestionHasNewReply() {
        
        return null;
    }

    @Override
    public JSONObject findQuestionUnresolved() {
        
        return null;
    }

    @Override
    public JSONObject findQuestionIsResolved() {
        
        return null;
    }

    @Override
    public JSONObject findQuestionIsClosed() {
        
        return null;
    }

    @Override
    public JSONObject findCommonByClassId(String classId) {
        JSONArray array = new JSONArray();
        JSONObject jsonObject =  new JSONObject();
        JSONObject json = null;
        if (StringUtils.isBlank(classId)) {
            try {
                Iterable<QuestionCommon> common = questionCommonService.findAllCommon();
                for (QuestionCommon questionCommon : common) {
                    json = convertQuestionCommon(questionCommon);
                    array.add(json);
                }
                jsonObject.put("result", array);
            } catch (Exception e) {
                jsonObject.put("success", "查询失败");
            }
        } else {
            try {
                Iterable<QuestionCommon> common = questionCommonService.findByClassId(classId);
                for (QuestionCommon questionCommon : common) {
                    json = convertQuestionCommon(questionCommon);
                    array.add(json);
                }
                jsonObject.put("result", array);
            } catch (Exception e) {
                jsonObject.put("success", "查询失败");
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject findQuestionByClassId(String classId) {
        
        return null;
    }

    @Override
    public JSONObject findQuestionByClassIdAndNew(String classId) {
        
        return null;
    }

    @Override
    public JSONObject findQuestionByClassIdAndResolved(String classId) {
        
        return null;
    }

    @Override
    public JSONObject findQuestionByUSerIdAndClassId(String userId, String classId) {
        
        return null;
    }

    @Override
    public JSONObject findQuestionAndReplyById(String questionId) {
        
        return null;
    }

    @Override
    public JSONObject findReplyByQuestionId(String questionId) {
        if (StringUtils.isBlank(questionId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject resData = new JSONObject();
        JSONObject result = new JSONObject();
        JSONArray jsonAry = new JSONArray();
        try {
            Iterable<QuestionReply> replys = questionReplyService.findByQuestionId(questionId);
            for (QuestionReply questionReply : replys) {
                jsonAry.add(convertQuestionReply(questionReply));
            }
          resData.put("replys", jsonAry);
          result.put("result", resData);
          result.put("success", "查询成功");
        } catch (Exception e) {
            result.put("success", "查询失败");
        }
        return result;
    }

    @Override
    public JSONObject addQuestionModel(String questionModelString) {
        QuestionModel model = new QuestionModel();
        model = requestParameterHandleUtils.convertRequestParams2Entity(model, questionModelString);
        if (model == null) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
           questionModelService.createQuestionModel(model);
            jsonObject.put("success", "添加成功");
        } catch (Exception e) {
            jsonObject.put("success", "添加失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateQuestionModelById(String questionModelString) {
        
        return null;
    }

    @Override
    public JSONObject deleteModelById(String ModelId) {
        if (StringUtils.isBlank(ModelId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            questionModelService.deleteQuestionModel(ModelId);
            jsonObject.put("success", "删除成功");
        } catch (Exception e) {
            jsonObject.put("success", "删除失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject deleteQuestionClassById(String classId) {
        if (StringUtils.isBlank(classId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            questionClassService.deleteQuestionClass(classId);
            jsonObject.put("success", "删除成功");
        } catch (Exception e) {
            jsonObject.put("success", "删除失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject addQuestionClass(String questionClassString) {
        QuestionClass questionClass = new QuestionClass();
        questionClass = requestParameterHandleUtils.convertRequestParams2Entity(questionClass, questionClassString);
        if (questionClass == null) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
         questionClassService.addQuestionClass(questionClass);
            jsonObject.put("success", "添加成功");
        } catch (Exception e) {
            jsonObject.put("success", "添加失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateQuestionClass(String classId, String questionClassString) {
        
        return null;
    }

    @Override
    public JSONObject findQuestionByTitle(String title) {
        if (StringUtils.isBlank(title)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            Iterable<Question> questions = questionService.findByTitle(title);
            for (Question question : questions) {
                jsonArray.add(question);
            }
            jsonObject.put("result", jsonArray);
        } catch (Exception e) {
           jsonObject.put("suceess", "查询失败");
        }
        return null;
    }

    @Override
    public JSONObject deleteReplyById(String replyId) {
        if (StringUtils.isBlank(replyId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            questionReplyService.deleteQuestionReply(replyId);
            jsonObject.put("success", "删除成功");
        } catch (Exception e) {
            jsonObject.put("success", "删除失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject deleteQuestionById(String questionId) {
        if (StringUtils.isBlank(questionId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
           questionService.deleteQuestion(questionId);
            jsonObject.put("success", "删除成功");
        } catch (Exception e) {
            jsonObject.put("success", "删除失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject deleteReplyByQuestionIdAndReplyId(String questionId, String replyId) {
        if (StringUtils.isBlank(questionId)||StringUtils.isBlank(replyId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        return null;
    }

    @Override
    public JSONObject updateReplyByAgreeNum(String replyId, String agreeNumber) {
        if (StringUtils.isBlank(replyId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            QuestionReply questionReply = questionReplyService.getQuestionReply(replyId);
            if (questionReply==null) {
                throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);
            }
            questionReply.setAgreeNumber(Integer.parseInt(agreeNumber));
            questionReply.setUpdateDate(new Date());
            questionReplyService.updateQuestionReply(questionReply);
            jsonObject.put("success", "修改成功");
        } catch (Exception e) {
            jsonObject.put("success", "修改失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateReplyByDisagreeNum(String replyId, String disagreeNumber) {
        if (StringUtils.isBlank(replyId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            QuestionReply questionReply = questionReplyService.getQuestionReply(replyId);
            if (questionReply==null) {
                throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);
            }
            questionReply.setAgreeNumber(Integer.parseInt(disagreeNumber));
            questionReply.setUpdateDate(new Date());
            questionReplyService.updateQuestionReply(questionReply);
            jsonObject.put("success", "修改成功");
        } catch (Exception e) {
            jsonObject.put("success", "修改失败");
        }
        return jsonObject;
    }

    @Override
    public JSONObject moveUpCommonQuestion(String questionCommonId) {
        
        return null;
    }

    @Override
    public JSONObject moveDownCommonQuestion(String questionCommonId) {
        
        return null;
    }

    @Override
    public JSONObject setRefObjectToQuestion(String questionId, String RefObject, String refObjectId) {
        if (StringUtils.isBlank(questionId)||StringUtils.isBlank(refObjectId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        Question question = questionService.getQuestion(questionId);
        if (question==null) {
            throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData); 
        }
        question.setRefObjectId(refObjectId);
        question.setRefObject(RefObject);
        question.setUpdateDate(new Date());
        JSONObject json = new JSONObject();
        try {
            questionService.updateQuestion(question);
            json.put("success", "修改成功");
        } catch (Exception e) {
            json.put("success", "修改失败");
        }
        return json;
    }

    @Override
    public JSONObject setRefObjectToCommon(String questioncommonId, String RefObject, String refObjectId) {
        if (StringUtils.isBlank(questioncommonId)||StringUtils.isBlank(refObjectId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        QuestionCommon questionCommon = questionCommonService.getById(questioncommonId);
        if (questionCommon==null) {
            throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData); 
        }
        questionCommon.setRefObjectId(refObjectId);
        questionCommon.setRefObject(RefObject);
        questionCommon.setUpdateDate(new Date());
        JSONObject json = new JSONObject();
        try {
            questionCommonService.updateQuestionCommon(questionCommon);
            json.put("success", "修改成功");
        } catch (Exception e) {
            json.put("success", "修改失败");
        }
        return json;
    }

    @Override
    public JSONObject moveQuestionToClass(String questionId, String classId) {
        if (StringUtils.isBlank(questionId)||StringUtils.isBlank(classId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        JSONObject json = new JSONObject();
        try {
            Question question = questionService.getQuestion(questionId);
            QuestionClass questionClass = questionClassService.getQuestionClass(classId);
            if (question==null||questionClass==null) {
                throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);
            }
            question.setClassId(classId);
            questionService.updateQuestion(question);
            json.put("success", "修改成功");
        } catch (Exception e) {
            json.put("success", "修改失败");
        }
        return json;
    }

    @Override
    public JSONObject changeQuestionClassId(String classId, String targetclassId) {
        if (StringUtils.isBlank(classId)||StringUtils.isBlank(targetclassId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        JSONObject json = new JSONObject();
        try {
            QuestionClass class1 = questionClassService.getQuestionClass(classId);
            QuestionClass class2 = questionClassService.getQuestionClass(targetclassId);
            if (class1==null||class2==null) {
                throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);  
            }
            Iterable<Question> questions = questionService.findByClassId(classId);
            for (Question question : questions) {
                question.setClassId(targetclassId);
               questionService.updateQuestion(question);
            }
            json.put("success", "修改成功");
            
        } catch (Exception e) {
            json.put("success", "修改失败");
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        return json;
    }

    @Override
    public JSONObject moveQuestionCommonToClass(String commonId, String classId) {
        if (StringUtils.isBlank(commonId)||StringUtils.isBlank(classId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        JSONObject json = new JSONObject();
        try {
           QuestionCommon common = questionCommonService.getById(commonId);
            QuestionClass questionClass = questionClassService.getQuestionClass(classId);
            if (common==null||questionClass==null) {
                throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);
            }
            common.setClassId(classId);
            questionCommonService.updateQuestionCommon(common);
            json.put("success", "修改成功");
        } catch (Exception e) {
            json.put("success", "修改失败");
        }
        return json;
    }

    @Override
    public JSONObject changeQuestionCommonClassId(String classId, String targetclassId) {
        if (StringUtils.isBlank(classId)||StringUtils.isBlank(targetclassId)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        JSONObject json = new JSONObject();
        try {
            QuestionClass class1 = questionClassService.getQuestionClass(classId);
            QuestionClass class2 = questionClassService.getQuestionClass(targetclassId);
            if (class1==null||class2==null) {
                throw new AWSClientException(GlobalHttpClientError.ResourceNotFound, ClientReason.NoFoundData);  
            }
            Iterable<QuestionCommon> questionCommons = questionCommonService.findByClassId(classId);
            for (QuestionCommon questionCommon : questionCommons) {
                questionCommon.setClassId(targetclassId);
                questionCommonService.updateQuestionCommon(questionCommon);
            }
            json.put("success", "修改成功");
            
        } catch (Exception e) {
            json.put("success", "修改失败");
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter); 
        }
        return json;
    }

    public JSONObject convertQuestionCommon(QuestionCommon questionCommon) {
        JSONObject jsonObject = null;
        if (questionCommon != null) {
            jsonObject = new JSONObject();
            jsonObject.put("id", questionCommon.getId());
            jsonObject.put("classId", questionCommon.getClassId());
            jsonObject.put("modelId", questionCommon.getModelId());
            jsonObject.put("title", questionCommon.getTitle());
            jsonObject.put("content", questionCommon.getContent());
            jsonObject.put("readNumber", questionCommon.getContent());
            jsonObject.put("answerContent", questionCommon.getAnswerContent());
            jsonObject.put("orderNumber", questionCommon.getOrderNumber());
            jsonObject.put("refObject", questionCommon.getRefObject());
            jsonObject.put("refObjectId", questionCommon.getRefObjectId());
            jsonObject.put("createDate",dateFormat.format(questionCommon.getCreateDate()));
            jsonObject.put("updateDate",dateFormat.format(questionCommon.getUpdateDate()));
            jsonObject.put("siteId", questionCommon.getSiteId());
        }
        return jsonObject;
    }
    
    public JSONObject convertQuestionReply(QuestionReply questionReply){
        JSONObject json = null;
        if (questionReply!=null) {
            json = new JSONObject();
            json.put("id", questionReply.getId());
            json.put("questionId", questionReply.getQuestionId());
            json.put("content", questionReply.getContent());
            json.put("readNumber", questionReply.getReadNumber());
            json.put("isCryptonym", questionReply.getIsCryptonym());
            json.put("cryptonymName", questionReply.getCryptonymName());
            json.put("contactInfo", questionReply.getContactInfo());
            json.put("createDate", dateFormat.format(questionReply.getCreateDate()));
            json.put("updateDate", dateFormat.format(questionReply.getUpdateDate()));
            json.put("agreeNumber", questionReply.getAgreeNumber());
            json.put("disagreeNumber", questionReply.getDisagreeNumber());
            json.put("siteUserId", questionReply.getSiteUserId());
            json.put("isAccepted", questionReply.getIsAccepted());
            json.put("siteId", questionReply.getSiteId());
        }
        return json;
    }
    
    public JSONObject converQuestion(Question question){
        JSONObject jsonObject  = null;
        if (question!=null) {
            jsonObject = new JSONObject();
            jsonObject.put("id", question.getId());
            jsonObject.put("modelId", question.getModelId());
            jsonObject.put("classId", question.getClassId());
            jsonObject.put("title", question.getTitle());
            jsonObject.put("content", question.getContent());
            jsonObject.put("readNumber", question.getReadNumber());
            jsonObject.put("isCryptonym", question.getIsCryptonym());
            jsonObject.put("cryptonymName", question.getCryptonymName());
            jsonObject.put("contactInfo", question.getContactInfo());
            jsonObject.put("createDate", dateFormat.format(question.getCreateDate()));
            jsonObject.put("updateDate", dateFormat.format(question.getUpdateDate()));
            jsonObject.put("siteUserId", question.getSiteUserId());
            jsonObject.put("receiverType", question.getReceiverType());
            jsonObject.put("receiverId", question.getReceiverId());
            jsonObject.put("status", question.getStatus());
            jsonObject.put("isTop", question.getIsTop());
            jsonObject.put("orderNumber", question.getOrderNumber());
            jsonObject.put("refObject", question.getRefObject());
            jsonObject.put("refObjectId", question.getRefObjectId());
            jsonObject.put("siteId", question.getSiteId());
        }
        return jsonObject;
    }
    
}
