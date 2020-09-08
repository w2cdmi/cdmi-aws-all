package pw.cdmi.msm.product.service;


import pw.cdmi.msm.product.model.ProductSaleStatus;
import pw.cdmi.msm.product.model.entities.Product;

/**
 * **********************************************************
 * <br/>
 * 接口类，提供系统产品的操作接口
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年5月26日
 ***********************************************************
 */

public interface ProductService {

    /**
     * 创建产品
     * 
     * @param Product
     * @return	是否创建成功
     */
    public boolean createProduct(Product product);

    /**
     * 编辑产品
     * 
     * @param Product
     * @return	是否编辑成功
     */
    public boolean updateProduct(Product product);

    /**
     * 删除产品
     * 
     * @param id	产品id
     * @return	是否删除成功
     */
    public boolean deleteProduct(String id);

    /**
     * 获取所有产品信息
     * 
     * @return
     */
    public Iterable<Product> findAllProductList();

    /**
     * 获取单个产品详细信息
     * 
     * @param id
     * @return
     */
    public Product getProduct(String id);

    /**
     * 修改产品状态
     * 
     * @param id
     * @param status
     * @return
     */
    public boolean setProductStatus(String id, ProductSaleStatus status);

    /**
     * 新增重名验证
     * 
     * @param id
     * @param name
     * @return
     */
    public boolean addProductNameIsRepeat(String name);

    /**
     * 编辑重名验证
     * 
     * @param id
     * @param name
     * @return
     */
    public boolean updateProductNameIsRepeat(String id, String name);

}
