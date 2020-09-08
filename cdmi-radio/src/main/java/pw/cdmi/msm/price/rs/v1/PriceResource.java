package pw.cdmi.msm.price.rs.v1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.msm.geo.ClientReason;
import pw.cdmi.msm.price.model.entity.PriceItemInfo;
import pw.cdmi.msm.price.model.entity.ProductPriceItem;
import pw.cdmi.msm.price.service.PriceService;
import pw.cdmi.open.ClientError;
import pw.cdmi.open.utils.RequestParameterHandleUtils;

/**
 * **********************************************************
 * <br/>
 * 提供产品定价对外接口
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */

@Path("/v1")
@Service("priceResource")
public class PriceResource {
    @Context
    private MessageContext context;

    @Autowired
    private PriceService priceService;

    @Resource
    private RequestParameterHandleUtils requestParameterHandleUtils;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @POST
    @Path("/productPriceItem")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject createProductPriceItem(String productPriceItemString) {

        ProductPriceItem productPriceItem = new ProductPriceItem();
        productPriceItem = requestParameterHandleUtils.convertRequestParams2Entity(productPriceItem,
            productPriceItemString);

        if (productPriceItem == null) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.NoFoundData);
        }

        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        productPriceItem.setCreateDate(new Date());

        success = priceService.createProductPriceItem(productPriceItem);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "添加成功！");
        } else {
            jsonObject.put("message", "添加失败！");
        }
        return jsonObject;
    }

    @PUT
    @Path("/productPriceItem")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateProductPriceItem(String productPriceItemString) {

        ProductPriceItem productPriceItem = new ProductPriceItem();
        productPriceItem = requestParameterHandleUtils.convertRequestParams2Entity(productPriceItem,
            productPriceItemString);

        if (productPriceItem == null) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.NoFoundData);
        }

        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        productPriceItem.setCreateDate(new Date());

        success = priceService.createProductPriceItem(productPriceItem);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "更新成功！");
        } else {
            jsonObject.put("message", "更新失败！");
        }
        return jsonObject;
    }

    @DELETE
    @Path("/productPriceItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteProductPriceItemById(@PathParam("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        success = priceService.deleteProductPriceItem(id);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "删除成功！");
        } else {
            jsonObject.put("message", "删除失败！");
        }
        return jsonObject;
    }

    @GET
    @Path("/productPriceItems/search")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray findProductPriceItemListByProductId(@QueryParam("productId") String productId) {
        JSONArray array = new JSONArray();
        JSONObject json = null;
        List<ProductPriceItem> list = priceService.findProductPriceItemListByProductId(productId);
        if (list != null && list.size() > 0) {
            for (ProductPriceItem productPriceItem : list) {
                json = productPriceItemParse(productPriceItem);
                array.add(json);
            }
        }
        return array;
    }

    @GET
    @Path("/productPriceItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getProductPriceItemById(@PathParam("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject json = new JSONObject();
        ProductPriceItem productPriceItem = priceService.getProductPriceItem(id);
        if (productPriceItem != null) {
            json = productPriceItemParse(productPriceItem);
        }
        return json;
    }

    @POST
    @Path("/priceItemInfo")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject createPriceItemInfo(String priceItemInfoString) {

        PriceItemInfo priceItemInfo = new PriceItemInfo();
        priceItemInfo = requestParameterHandleUtils.convertRequestParams2Entity(priceItemInfo, priceItemInfoString);

        if (priceItemInfo == null) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.NoFoundData);
        }

        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        priceItemInfo.setUpdateDate(new Date());

        success = priceService.createPriceItemInfo(priceItemInfo);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "添加成功！");
        } else {
            jsonObject.put("message", "添加失败！");
        }
        return jsonObject;
    }

    @PUT
    @Path("/priceItemInfo")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updatePriceItemInfo(String priceItemInfoString) {

        PriceItemInfo priceItemInfo = new PriceItemInfo();
        priceItemInfo = requestParameterHandleUtils.convertRequestParams2Entity(priceItemInfo, priceItemInfoString);

        if (priceItemInfo == null) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.NoFoundData);
        }

        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        priceItemInfo.setUpdateDate(new Date());

        success = priceService.updatePriceItemInfo(priceItemInfo);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "添加成功！");
        } else {
            jsonObject.put("message", "添加失败！");
        }
        return jsonObject;
    }

    @DELETE
    @Path("/priceItemInfo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deletePriceItemInfo(@PathParam("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        success = priceService.deletePriceItemInfo(id);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "删除成功！");
        } else {
            jsonObject.put("message", "删除失败！");
        }
        return jsonObject;
    }

    @GET
    @Path("/priceItemInfos/search")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray findPriceItemInfoListByPriceItemId(@QueryParam("priceItemId") String priceItemId) {
        JSONArray array = new JSONArray();
        JSONObject json = null;
        List<PriceItemInfo> list = priceService.findPriceItemInfoListByPriceItemId(priceItemId);
        if (list != null && list.size() > 0) {
            for (PriceItemInfo priceItemInfo : list) {
                json = PriceItemInfoParse(priceItemInfo);
                array.add(json);
            }
        }
        return array;
    }

    @GET
    @Path("/productPriceItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getPriceItemInfo(@PathParam("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new AWSClientException(ClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject json = new JSONObject();
        PriceItemInfo priceItemInfo = priceService.getPriceItemInfo(id);
        if (priceItemInfo != null) {
            json = PriceItemInfoParse(priceItemInfo);
        }
        return json;
    }

    private JSONObject productPriceItemParse(ProductPriceItem productPriceItem) {
        JSONObject json = new JSONObject();
        json.put("id", productPriceItem.getId());
        json.put("productId", productPriceItem.getProduct_id());
        json.put("name", productPriceItem.getName());
        if (productPriceItem.getCreateDate() != null) {
            json.put("createDate", dateFormat.format(productPriceItem.getCreateDate()));
        }
        json.put("description", productPriceItem.getDescription());
        return json;
    }

    private JSONObject PriceItemInfoParse(PriceItemInfo priceItemInfo) {
        JSONObject json = new JSONObject();
        json.put("id", priceItemInfo.getId());
        json.put("priceItemId", priceItemInfo.getPriceItem_Id());
        json.put("content", priceItemInfo.getContent());
        json.put("Standard", priceItemInfo.getStandard());
        json.put("unit", priceItemInfo.getUnit());
        json.put("price", priceItemInfo.getPrice());
        json.put("priceModel", priceItemInfo.getPriceModel());
        if (priceItemInfo.getPriceModel() != null) {
            json.put("priceModelText", priceItemInfo.getPriceModel().getText());
        }
        if (priceItemInfo.getUpdateDate() != null) {
            json.put("updateDate", priceItemInfo.getUpdateDate());
        }
        json.put("description", priceItemInfo.getDescription());
        return json;
    }
}
