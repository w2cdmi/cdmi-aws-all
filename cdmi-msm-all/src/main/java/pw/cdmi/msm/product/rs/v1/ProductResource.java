package pw.cdmi.msm.product.rs.v1;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import pw.cdmi.msm.product.model.ProductSaleStatus;
import pw.cdmi.msm.product.rs.model.ShowProduct;

/**
 * ********************************************************** 
 * <br/>
 * 提供产品模块对外接口
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */

@Path("/v1")
public interface ProductResource {

    @POST
    @Path("/product")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject createProduct(String productString);

    @PUT
    @Path("/product")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public JSONObject updateProduct(String productString);

    @GET
    @Path("/products")
    @Produces({ "application/xml;charset=utf-8", "application/json;charset=utf-8" })
    public List<ShowProduct> getProducts();

    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ShowProduct getProductById(@PathParam("id") String id);

    @DELETE
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject deleteProductById(@PathParam("id") String id);

    @Path("/product/{id}/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject setProductStatusById(@PathParam("id") String id, @PathParam("status") ProductSaleStatus status);

    @GET
    @Path("/product/{name}/isRepeat")
    @Produces("application/json;charset=utf-8")
    public boolean updateProductNameIsRepeat(@PathParam("name") String name);

    @GET
    @Path("/product/{id}/{name}/isRepeat")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updateProductNameIsRepeat(@PathParam("id") String id, @PathParam("name") String name);

    @GET
    @Path("/products/serach")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getProductByNameAndTeamName(@QueryParam("name") String name, @QueryParam("teamName") String teamName);

}
