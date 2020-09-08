package pw.cdmi.msm.price.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.cdmi.core.http.exception.AWSServiceException;
import pw.cdmi.core.http.exception.SystemReason;
import pw.cdmi.msm.praise.repositories.PraiseRepository;
import pw.cdmi.msm.price.model.entities.PriceItemInfo;
import pw.cdmi.msm.price.model.entities.ProductPriceItem;
import pw.cdmi.msm.price.service.PriceService;
import pw.cdmi.msm.product.rs.repositories.PriceItemInfoRepository;
import pw.cdmi.msm.product.rs.repositories.ProductPriceItemRepository;

/**
 * **********************************************************
 * <br/>
 * 实现类，提供系统产品定价接口的具体实现方法
 * 
 * @author wangbojun
 * @version cdm Service Platform, 2016年6月16日
 ***********************************************************
 */
@Service
public class PriceServiceImpl implements PriceService {

    private static Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    @Autowired
    private PriceItemInfoRepository priceItemInfoRespository;

    @Autowired
    private ProductPriceItemRepository priceItemRespository;
    
    @Override
    @Transactional
    public boolean createProductPriceItem(ProductPriceItem productPriceItem) {
        boolean flag = false;
        try {
        	priceItemRespository.save(productPriceItem);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean updateProductPriceItem(ProductPriceItem productPriceItem) {
        boolean flag = false;
        try {
            ProductPriceItem productPriceItem2 = priceItemRespository.findOne(productPriceItem.getId());
            if (productPriceItem2 != null) {
            	priceItemRespository.save(productPriceItem);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean deleteProductPriceItem(String id) {
        boolean flag = false;
        try {
            ProductPriceItem productPriceItem = priceItemRespository.findOne(id);
            if (productPriceItem != null) {
            	priceItemRespository.delete(productPriceItem);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    public List<ProductPriceItem> findProductPriceItemListByProductId(String productId) {
        List<ProductPriceItem> list = new ArrayList<ProductPriceItem>();
        try {
            list = priceItemRespository.findByProductId(productId);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public ProductPriceItem getProductPriceItem(String id) {
        ProductPriceItem productPriceItem = null;
        try {
            productPriceItem = priceItemRespository.findOne(id);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return productPriceItem;
    }

    @Override
    @Transactional
    public boolean createPriceItemInfo(PriceItemInfo priceItemInfo) {
        boolean flag = false;
        try {
        	priceItemInfoRespository.save(priceItemInfo);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean updatePriceItemInfo(PriceItemInfo priceItemInfo) {
        boolean flag = false;
        try {
            PriceItemInfo priceItemInfo2 = priceItemInfoRespository.findOne(priceItemInfo.getId());
            if (priceItemInfo2 != null) {
            	priceItemInfoRespository.save(priceItemInfo);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean deletePriceItemInfo(String id) {
        boolean flag = false;
        try {
            PriceItemInfo priceItemInfo = priceItemInfoRespository.findOne(id);
            if (priceItemInfo != null) {
            	priceItemInfoRespository.delete(priceItemInfo);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return flag;
    }

    @Override
    public List<PriceItemInfo> findPriceItemInfoListByPriceItemId(String priceItemId) {
        List<PriceItemInfo> list = new ArrayList<PriceItemInfo>();
        try {
            list = priceItemInfoRespository.findPriceItemInfoByItemId(priceItemId);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return list;
    }

    @Override
    public PriceItemInfo getPriceItemInfo(String id) {
        PriceItemInfo priceItemInfo = null;
        try {
            priceItemInfo = priceItemInfoRespository.findOne(id);
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            throw new AWSServiceException(SystemReason.SQLError);
        }
        return priceItemInfo;
    }

}
