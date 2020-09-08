package pw.cdmi.msm.product.rs.v1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;
import pw.cdmi.core.http.exception.AWSClientException;
import pw.cdmi.core.http.exception.ClientError;
import pw.cdmi.core.http.exception.ClientReason;
import pw.cdmi.core.http.exception.GlobalHttpClientError;
import pw.cdmi.core.http.utils.RequestParameterHandleUtils;
import pw.cdmi.msm.product.model.ProductSaleStatus;
import pw.cdmi.msm.product.model.entities.Product;
import pw.cdmi.msm.product.rs.model.ShowProduct;
import pw.cdmi.msm.product.service.ProductService;

/**
 * ********************************************************** <br/>
 * 提供产品模块对外接口
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月26日
 */
/**
 * **********************************************************
  <br/>
 * 提供产品模块对外接口实现方法
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月21日
 ***********************************************************
 */
@Service("productResource")
public class ProductResourceImpl implements ProductResource {

    @Autowired
    private ProductService productService;

    @Resource
    private RequestParameterHandleUtils requestParameterHandleUtils;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public JSONObject createProduct(String productString) {

        Product product = new Product();
        product = requestParameterHandleUtils.convertRequestParams2Entity(product, productString);

        if (product == null) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }

        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        product.setPublishDate(new Date());

        success = productService.createProduct(product);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "添加成功！");
        } else {
            jsonObject.put("message", "添加失败！");
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateProduct(String productString) {

        Product product = new Product();
        product = requestParameterHandleUtils.convertRequestParams2Entity(product, productString);

        if (product == null || StringUtils.isBlank(product.getId())) {
            throw new AWSClientException(ClientError.NoFoundData, ClientReason.NoFoundData);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;

        success = productService.updateProduct(product);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "更新成功！");
        } else {
            jsonObject.put("message", "更新失败！");
        }
        return jsonObject;
    }

    @Override
    public List<ShowProduct> getProducts() {
        List<ShowProduct> showProductList = new ArrayList<ShowProduct>();
        Iterable<Product> list = productService.findAllProductList();
        ShowProduct showProduct = null;
        for (Product product : list) {
            showProduct = convertProduct2ShowProduct(product);
            showProductList.add(showProduct);
        }
        return showProductList;
    }

    @Override
    public ShowProduct getProductById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        ShowProduct showProduct = new ShowProduct();
        Product product = productService.getProduct(id);
        if (product != null) {
            showProduct = convertProduct2ShowProduct(product);
        }
        return showProduct;
    }

    @Override
    public JSONObject deleteProductById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        success = productService.deleteProduct(id);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "删除成功！");
        } else {
            jsonObject.put("message", "删除失败！");
        }
        return jsonObject;
    }

    @Override
    public JSONObject setProductStatusById(String id, ProductSaleStatus status) {
        if (StringUtils.isBlank(id) && status != null) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        success = productService.setProductStatus(id, status);
        jsonObject.put("success", success);
        if (success) {
            jsonObject.put("message", "设置成功！");
        } else {
            jsonObject.put("message", "设置失败！");
        }
        return jsonObject;
    }

    @Override
    public boolean updateProductNameIsRepeat(String name) {
        if (StringUtils.isBlank(name)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        success = productService.addProductNameIsRepeat(name);
        jsonObject.put("success", success);
        return success;
    }

    @Override
    public boolean updateProductNameIsRepeat(String id, String name) {
        if (StringUtils.isBlank(id) && StringUtils.isBlank(name)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        success = productService.updateProductNameIsRepeat(id, name);
        jsonObject.put("success", success);
        return success;
    }

    @Override
    public boolean getProductByNameAndTeamName(String name, String teamName) {
        if (StringUtils.isBlank(name) && StringUtils.isBlank(teamName)) {
            throw new AWSClientException(GlobalHttpClientError.InvalidParameter, ClientReason.InvalidParameter);
        }
        JSONObject jsonObject = new JSONObject();
        boolean success = false;
        // success = productService.updateProductNameIsRepeat(id, name);
        jsonObject.put("success", success);
        return success;
    }

    private ShowProduct convertProduct2ShowProduct(Product product) {
        ShowProduct showProduct = new ShowProduct();
        showProduct.setId(product.getId());
        showProduct.setName(product.getName());
        showProduct.setType(product.getType().toString());
        if (product.getType() != null) {
            showProduct.setType(product.getType());
        }
        showProduct.setCompanyId(product.getProviderId());
        if (product.getPublishDate() != null) {
            showProduct.setPublishDate(product.getPublishDate());
        }
        showProduct.setSaleStatus(product.getSaleStatus().toString());
        if (product.getSaleStatus() != null) {
            showProduct.setSaleStatusText(product.getSaleStatus().getText());
        }
        showProduct.setIntroduce(product.getPresentation());
        showProduct.setOverview(product.getOverview());
        return showProduct;
    }

}
