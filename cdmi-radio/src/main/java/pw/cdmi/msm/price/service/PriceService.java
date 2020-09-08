package pw.cdmi.msm.price.service;

import java.util.List;

import pw.cdmi.msm.price.model.entity.PriceItemInfo;
import pw.cdmi.msm.price.model.entity.ProductPriceItem;

/**
 * **********************************************************
 * <br/>
 * 接口类，提供系统产品项目定价接口
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */
public interface PriceService {
    /**
     * 创建定价项
     * 
     * @param productPriceItem
     * @return  是否创建成功
     */
    public boolean createProductPriceItem(ProductPriceItem productPriceItem);

    /**
     * 编辑产品定价项
     * 
     * @param ProductPriceItem
     * @return  是否编辑成功
     */
    public boolean updateProductPriceItem(ProductPriceItem productPriceItem);

    /**
     * 删除产品定价项
     * 
     * @param id    产品id
     * @return  是否删除成功
     */
    public boolean deleteProductPriceItem(String id);

    /**
     * 根据产品编号，获取改产品所有定价项
     * 
     * @param productId
     * @return
     */
    public List<ProductPriceItem> findProductPriceItemListByProductId(String productId);

    /**
     * 获取单个产品定价项详细信息
     * 
     * @param id
     * @return
     */
    public ProductPriceItem getProductPriceItem(String id);

    /**
     * 创建产品定价项，一种收费情况
     * 
     * @param PriceItemInfo
     * @return  是否创建成功
     */
    public boolean createPriceItemInfo(PriceItemInfo priceItemInfo);

    /**
     * 编辑计费项
     * 
     * @param PriceItemInfo
     * @return  是否编辑成功
     */
    public boolean updatePriceItemInfo(PriceItemInfo priceItemInfo);

    /**
     * 删除计费项
     * 
     * @param id    计费项编号
     * @return  是否删除成功
     */
    public boolean deletePriceItemInfo(String id);

    /**
     * 获取定价项下所有计费信息
     * 
     * @return
     */
    public List<PriceItemInfo> findPriceItemInfoListByPriceItemId(String priceItemId);

    /**
     * 获取单个计费信息
     * 
     * @param id
     * @return
     */
    public PriceItemInfo getPriceItemInfo(String id);
}
