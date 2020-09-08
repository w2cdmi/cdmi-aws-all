package pw.cdmi.msm.alarm.rs.v1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.PATCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.msm.alarm.model.AlarmLevel;
import pw.cdmi.msm.alarm.model.HandleStatus;
import pw.cdmi.msm.alarm.model.entity.Alarm;
import pw.cdmi.msm.alarm.model.entity.AlarmDate;
import pw.cdmi.msm.alarm.service.AlarmService;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.open.ClientError;
import pw.cdmi.open.utils.RequestParameterHandleUtils;

/**
 * **********************************************************
 * <br/>
 * 提供业务告警对外rest接口
 * 
 * @author Liujh
 * @version cdm Service Platform, 2016年5月31日
 ***********************************************************
 */
@Path("/v1")
@Service("alarmResource")
public class AlarmResource {

    private static Logger log = LoggerFactory.getLogger(AlarmResource.class);

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Context
    private MessageContext context;

    @Autowired
    private AlarmService alarmService;

    //@Autowired
    //private ProductService productService;

    //@Autowired
    //private EmployeeService employeeService;

    @Resource
    private RequestParameterHandleUtils requestParameterHandleUtils;

    @POST
    @Path("/alarm")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject createAlarm(String alarming) {
        Alarm alarm = new Alarm();
        alarm = requestParameterHandleUtils.convertRequestParams2Entity(alarm, alarming);
        if (alarm == null || "".equals(alarm.getId())) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.NoFoundData);
        }
        JSONObject json = new JSONObject();
        try {
            alarmService.createAlarm(alarm);
            json.put("success", true);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            json.put("success", false);
        }
        return json;
    }

    /**
     * 
     * 忽略此类告警信息
     * 
     * @param alarmId 告警信息编号
     * @return
     */
    @PATCH
    @Path("/alarm/ignore/{alarmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject ignoreAlarm(@PathParam("alarmId") String alarmId) {
        if (alarmId == null || "".equals(alarmId)) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject json = new JSONObject();
        // 从session获取当前用户
        //SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Alarm alarm = alarmService.getAlarm(alarmId);
        if (alarm.getStatus().getValue() > HandleStatus.UNTREATED.getValue()) {
            json.put("success", false);
            return json;
        }
        try {
            alarmService.resolveCurrentAlarm(alarmId, null, HandleStatus.IGNORE, null);
            json.put("success", true);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            json.put("success", false);
        }
        return json;
    }

    /**
     * 
     * 处理告警信息
     * 
     * @param alarmId 告警信息编号
     * @param status 告警状态
     * @param handleWay 处理方式
     * @return
     */
    @PATCH
    @Path("/alarm/process/{alarmId}/{status}/{handleWay}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject processAlarm(@PathParam("alarmId") String alarmId, @PathParam("status") HandleStatus status,
        @PathParam("handleWay") String handleWay) {
        if (alarmId == null || "".equals(alarmId) || status.getValue() <= HandleStatus.UNTREATED.getValue()) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject json = new JSONObject();
        // 从session获取当前用户
        // SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Alarm alarm = alarmService.getAlarm(alarmId);
        if (alarm.getStatus().getValue() > HandleStatus.UNTREATED.getValue()) {
            json.put("success", false);
            return json;
        }
        try {
            alarmService.resolveCurrentAlarm(alarmId, null, status, handleWay);
            json.put("success", true);
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            json.put("success", false);
        }
        return json;
    }

    @GET
    @Path("/alarm/{alarmId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getAlarm(@PathParam("alarmId") String alarmId) {
        if (alarmId == null || "".equals(alarmId)) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject json = new JSONObject();
        Alarm alarm = alarmService.getAlarm(alarmId);
        if (alarm != null) {
            json.put("id", alarm.getId());
            json.put("content", alarm.getContent());
            json.put("handleWay", alarm.getHandleWay());
            if (alarm.getStatus() != null) {
                json.put("status", alarm.getStatus().getText());
            }
            if (alarm.getAlarmLevel() != null) {
                json.put("alarmLevel", alarm.getAlarmLevel().getText());
            }
            if (alarm.getNotifyWay() != null) {
                json.put("notifyWay", alarm.getNotifyWay().getText());
            }
            // 根据对象id，查询出该对象信息
//            Product product = productService.getProduct(alarm.getObjectId());
//            if (product != null) {
//                json.put("objectName", product.getName());
//            }
            // 根据用户id，查询该用户名称
//            if (alarm.getHandleUserId() != null) {
//                Employee employee = employeeService.getSingleEmployeeById(alarm.getHandleUserId());
//                if (employee != null) {
//                    json.put("handleUserId", alarm.getHandleUserId());
//                    json.put("handleUserName", employee.getName());
//                    json.put("handleDate", df.format(alarm.getHandleDate()));
//                }
//            }
            // 根据告警id查询出告警时间列表
            List<AlarmDate> list = alarmService.findAlarmDateByAlarmId(alarm.getId());
            if (list.size() > 0) {
                json.put("firstTime", df.format(list.get(list.size() - 1).getAlarmDate()));
                json.put("currentTime", df.format(list.get(0).getAlarmDate()));
                json.put("times", list.size());
            }
        }
        return json;
    }

    @GET
    @Path("/alarms")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Alarm> getAlarms() {
        Iterable<Alarm> alarms = alarmService.findAllAlarm();
        return alarms;
    }

    /**
     * 
     * 查询未处理的告警信息列表
     * 
     * @return 告警信息列表
     */
    @GET
    @Path("/untreatedAlarms")
    @Produces({MediaType.APPLICATION_JSON})
    public JSONObject findUntreatedAlarm() {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        Page<AlarmDate> page = alarmService.findAlarmDatePageByStatus(HandleStatus.UNTREATED, 0, 10);
        for (AlarmDate alarmDate : page.getContent()) {
            JSONObject json = new JSONObject();
            Alarm alarm = alarmService.getAlarm(alarmDate.getAlarmId());
            json.put("alarmId", alarm.getId());
            json.put("level", alarm.getAlarmLevel());
            json.put("content", alarm.getContent());
            if(alarmDate.getAlarmDate() != null){
                json.put("currentTime", df.format(alarmDate.getAlarmDate()));
            }
            array.add(json);
        }
        result.put("datas", array);
        result.put("count", page.getTotalElements());
        
        return result;
    }
    
    /**
     * 
     * 根据条件返回告警历史记录列表
     * 
     * @param status 处理状态
     * @param level 告警级别
     * @param objectName 对象名称
     * @param pageNo 当前页数
     * @param pageSize 每页大小
     * @return 告警历史记录列表
     */
    @GET
    @Path("/alarms/search")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject findAlarmByCondition(@QueryParam("content") String content, @QueryParam("status") String status,
        @QueryParam("level") String level, @QueryParam("objectName") String objectName,
        @QueryParam("pageNo") int pageNo, @QueryParam("pageSize") int pageSize) {
        JSONObject jsonObject = new JSONObject();
        Iterable<Alarm> alarms = alarmService.findAlarmByCondition(content, HandleStatus.fromEnumName(status),
            AlarmLevel.fromEnumName(level), objectName, pageNo, pageSize);
        JSONArray array = new JSONArray();
        for (Alarm alarm : alarms) {
            JSONObject json = new JSONObject();
            json.put("alarmId", alarm.getId());
            json.put("content", alarm.getContent());
            json.put("notifyWay", alarm.getNotifyWay());
            json.put("level", alarm.getAlarmLevel());
            json.put("status", alarm.getStatus());
            // 判断对象id是否为空,获取关联对象
//            if (alarm.getObjectId() != null) {
//                Product product = productService.getProduct(alarm.getObjectId());
//                if (product != null) {
//                    json.put("objectName", product.getName());
//                }
//            }
            // 获取告警时间列表
            List<AlarmDate> adList = alarmService.findAlarmDateByAlarmId(alarm.getId());
            if (adList.size() > 0) {
                json.put("currentTime", df.format((adList.get(0).getAlarmDate())));
                json.put("times", adList.size());
            }
            array.add(json);
        }
        jsonObject.put("datas", array);
        long count = alarmService.getCountAlarmByCondition(content, HandleStatus.fromEnumName(status),
            AlarmLevel.fromEnumName(level), objectName);
        long totalPages = count % pageSize > 0 ? (count / pageSize) + 1 : count / pageSize;
        jsonObject.put("count", count);
        jsonObject.put("totalPages", totalPages);
        return jsonObject;
    }

    /**
     * 
     * 获取告警状态枚举
     * 
     * @return
     */
    @GET
    @Path("alarm/handleStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getAllAlarmStatus() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {

            for (HandleStatus status : HandleStatus.values()) {
                Map<String, String> map = new HashMap<String, String>();
                if (status.equals(HandleStatus.PROCESSING)) {
                    continue;
                }
                map.put("name", status.getText());
                map.put("value", status.toString());
                list.add(map);
            }
            return JSONArray.fromObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
